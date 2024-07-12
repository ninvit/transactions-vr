package app.controller;

import app.dto.TransacaoDTO;
import app.enums.ErroTransacao;
import app.exception.CartaoNotFoundException;
import app.service.CartaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartaoController.class)
public class CartaoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CartaoService cartaoService;

    @InjectMocks
    private CartaoController cartaoController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(cartaoController).build();
    }

    @Test
    public void testEfetuarTransacao() throws Exception {
        // Arrange
        TransacaoDTO transacaoDTO = new TransacaoDTO(123456789L, "senha1234", 500.0);
        String json = new ObjectMapper().writeValueAsString(transacaoDTO);

        // Act and Assert
        mockMvc.perform(post("/api/cartoes/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Transação realizada com sucesso."));

        verify(cartaoService, times(1)).efetuarTransacao(transacaoDTO);
    }

    @Test
    public void testEfetuarTransacaoCartaoNaoEncontrado() throws Exception {
        // Arrange
        TransacaoDTO transacaoDTO = new TransacaoDTO(123456789L, "senha1234", 500.0);
        String json = new ObjectMapper().writeValueAsString(transacaoDTO);
        doThrow(new CartaoNotFoundException(ErroTransacao.CARTAO_INEXISTENTE)).when(cartaoService).efetuarTransacao(transacaoDTO);

        // Act and Assert
        mockMvc.perform(post("/api/cartoes/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(ErroTransacao.CARTAO_INEXISTENTE.name()));

        verify(cartaoService, times(1)).efetuarTransacao(transacaoDTO);
    }
}
