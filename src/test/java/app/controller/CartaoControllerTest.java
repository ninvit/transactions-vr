package app.controller;

import app.dto.TransacaoDTO;
import app.model.Cartao;
import app.repository.CartaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void buscarTodosCartoes() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(6549873025634501L);
        cartao.setSenha("1234");
        cartao.setSaldo(500.0);

        when(cartaoRepository.findAll()).thenReturn(Collections.singletonList(cartao));

        mockMvc.perform(get("/api/cartoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroCartao").value(6549873025634501L))
                .andExpect(jsonPath("$[0].senha").value("1234"))
                .andExpect(jsonPath("$[0].saldo").value(500.0));
    }

    @Test
    public void buscarSaldoPorId() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setId(1L);
        cartao.setNumeroCartao(6549873025634501L);
        cartao.setSenha("1234");
        cartao.setSaldo(500.0);

        when(cartaoRepository.findById(anyLong())).thenReturn(Optional.of(cartao));

        mockMvc.perform(get("/api/cartoes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.numeroCartao").value(6549873025634501L))
                .andExpect(jsonPath("$.senha").value("1234"))
                .andExpect(jsonPath("$.saldo").value(500.0));
    }

    @Test
    public void criaNovoCartao() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(6549873025634501L);
        cartao.setSenha("1234");
        cartao.setSaldo(500.0);

        when(cartaoRepository.save(any(Cartao.class))).thenReturn(cartao);

        mockMvc.perform(post("/api/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartao)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroCartao").value(6549873025634501L))
                .andExpect(jsonPath("$.senha").value("1234"))
                .andExpect(jsonPath("$.saldo").value(500.0));
    }

    @Test
    public void efetuarTransacaoComSaldoSuficiente() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(6549873025634501L);
        cartao.setSenha("1234");
        cartao.setSaldo(500.0);

        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao(6549873025634501L);
        transacaoDTO.setSenhaCartao("1234");
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
    public void efetuarTransacaoComSaldoInsuficiente() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(6549873025634501L);
        cartao.setSenha("1234");
        cartao.setSaldo(5.0);

        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao(6549873025634501L);
        transacaoDTO.setSenhaCartao("1234");
        transacaoDTO.setValor(10.0);

        when(cartaoRepository.findByNumeroCartao(anyLong())).thenReturn(Optional.of(cartao));

        mockMvc.perform(post("/api/cartoes/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Saldo insuficiente."));
    }
}
