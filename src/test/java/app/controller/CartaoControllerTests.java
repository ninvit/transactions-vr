package app.controller;

import app.dto.TransacaoDTO;
import app.exception.CartaoExistenteException;
import app.model.Cartao;
import app.repository.CartaoRepository;
import app.service.CartaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartaoController.class)
@AutoConfigureMockMvc
public class CartaoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartaoRepository cartaoRepository;

    @MockBean
    private CartaoService cartaoService;


//    @Test
//    public void testBuscarTodosCartoes() throws Exception {
//        Cartao cartao1 = new Cartao("1234567890123456", "1234", 500.0);
//        Cartao cartao2 = new Cartao(6543210987654321L, "5678", 800.0);
//        List<Cartao> cartoes = Arrays.asList(cartao1, cartao2);
//
//        when(cartaoRepository.findAll()).thenReturn(cartoes);
//
//        mockMvc.perform(get("/api/cartoes")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].numeroCartao").value("1234567890123456"))
//                .andExpect(jsonPath("$[1].numeroCartao").value(6543210987654321L));
//    }
//
//    @Test
//    public void testBuscarSaldoPorNumeroCartaoExistente() throws Exception {
//        Cartao cartao = new Cartao("1234567890123456", "1234", 500.0);
//        when(cartaoRepository.findByNumeroCartao("1234567890123456")).thenReturn(Optional.of(cartao));
//
//        mockMvc.perform(get("/api/cartoes/{numeroCartao}", "1234567890123456")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.numeroCartao").value("1234567890123456"))
//                .andExpect(jsonPath("$.senha").value("1234"))
//                .andExpect(jsonPath("$.saldo").value(500.0));
//    }
//
//    @Test
//    public void testBuscarSaldoPorNumeroCartaoNaoExistente() throws Exception {
//        when(cartaoRepository.findByNumeroCartao("1234567890123456")).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/api/cartoes/{numeroCartao}", "1234567890123456")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }

    @Test
    public void testSalvarCartaoNovo() throws Exception {
        Cartao cartao = new Cartao("1234567890123456", "1234", 500.0);

        mockMvc.perform(post("/api/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"numeroCartao\": \"1234567890123456\", \"senha\": \"1234\", \"saldo\": 500.0 }"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.numeroCartao").value("1234567890123456"))
                .andExpect(jsonPath("$.senha").value("1234"))
                .andExpect(jsonPath("$.saldo").value(500.0));
    }

//    @Test
//    public void testSalvarCartaoExistente() throws Exception {
//        Cartao cartaoExistente = new Cartao("1234567890123456", "1234", 500.0);
//
//        doThrow(new CartaoExistenteException(cartaoExistente)).when(cartaoService).salvarCartao(any(Cartao.class));
//
//        mockMvc.perform(post("/api/cartoes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{ \"numeroCartao\": \"1234567890123456\", \"senha\": \"1234\", \"saldo\": 500.0 }"))
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.numeroCartao").value("1234567890123456"))
//                .andExpect(jsonPath("$.senha").value("1234"));
//    }
//    @Test
//    public void testEfetuarTransacao() throws Exception {
//        TransacaoDTO transacaoDTO = new TransacaoDTO("1234567890123456", "1234", 100.0);
//
//        mockMvc.perform(put("/api/cartoes/transacao")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{ \"numeroCartao\": \"1234567890123456\", \"senhaCartao\": \"1234\", \"valor\": 100.0 }"))
//                .andExpect(status().isCreated())
//                .andExpect(content().string("OK"));
//    }
}
