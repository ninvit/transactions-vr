package app.controller;

import app.domain.dto.TransacaoDTO;
import app.domain.model.Cartao;
import app.domain.repository.CartaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartaoController.class)
public class CartaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartaoRepository cartaoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void efetuarTransacao_comSaldoSuficiente() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(6549873025634501L);
        cartao.setSenha("1234");
        cartao.setSaldo(500.0);

        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao(6549873025634501L);
        transacaoDTO.setSenha("1234");
        transacaoDTO.setValor(10.0);

        when(cartaoRepository.findByNumeroCartao(anyLong())).thenReturn(Optional.of(cartao));
        when(cartaoRepository.save(any(Cartao.class))).thenReturn(cartao);

        mockMvc.perform(post("/api/cartoes/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Transação realizada com sucesso."));
    }

    @Test
    public void efetuarTransacao_comSaldoInsuficiente() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(6549873025634501L);
        cartao.setSenha("1234");
        cartao.setSaldo(5.0);

        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao(6549873025634501L);
        transacaoDTO.setSenha("1234");
        transacaoDTO.setValor(10.0);

        when(cartaoRepository.findByNumeroCartao(anyLong())).thenReturn(Optional.of(cartao));

        mockMvc.perform(post("/api/cartoes/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoDTO)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$").value("SALDO_INSUFICIENTE"));
    }

    @Test
    public void efetuarTransacao_cartaoNaoEncontrado() throws Exception {
        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao(6549873025634501L);
        transacaoDTO.setSenha("1234");
        transacaoDTO.setValor(10.0);

        when(cartaoRepository.findByNumeroCartao(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/cartoes/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoDTO)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$").value("CARTAO_INEXISTENTE"));
    }

    @Test
    public void efetuarTransacao_senhaIncorreta() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(6549873025634501L);
        cartao.setSenha("1234");
        cartao.setSaldo(500.0);

        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao(6549873025634501L);
        transacaoDTO.setSenha("4321");  // senha incorreta
        transacaoDTO.setValor(10.0);

        when(cartaoRepository.findByNumeroCartao(anyLong())).thenReturn(Optional.of(cartao));

        mockMvc.perform(post("/api/cartoes/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoDTO)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$").value("SENHA_INVALIDA"));
    }
}
