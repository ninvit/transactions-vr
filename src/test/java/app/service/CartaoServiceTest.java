package app.service;

import app.dto.TransacaoDTO;
import app.model.Cartao;
import app.repository.CartaoRepository;
import app.enums.ErroTransacao;
import app.exception.CartaoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CartaoServiceTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CartaoService cartaoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEfetuarTransacaoSucesso() {
        // Arrange
        TransacaoDTO transacaoDTO = new TransacaoDTO(123456789L, "senha1234", 500.0);
        Cartao cartao = new Cartao(1L, 123456789L, "senha1234", 1000.0);
        when(cartaoRepository.findByNumeroCartao(transacaoDTO.getNumeroCartao())).thenReturn(Optional.of(cartao));

        // Act
        assertDoesNotThrow(() -> cartaoService.efetuarTransacao(transacaoDTO));

        // Assert
        assertEquals(500.0, cartao.getSaldo());
        verify(cartaoRepository, times(1)).findByNumeroCartao(transacaoDTO.getNumeroCartao());
        verify(cartaoRepository, times(1)).save(cartao);
    }

    @Test
    public void testEfetuarTransacaoCartaoNaoEncontrado() {
        // Arrange
        TransacaoDTO transacaoDTO = new TransacaoDTO(123456789L, "senha1234", 500.0);
        when(cartaoRepository.findByNumeroCartao(transacaoDTO.getNumeroCartao())).thenReturn(Optional.empty());

        // Act and Assert
        CartaoNotFoundException exception = assertThrows(CartaoNotFoundException.class,
                () -> cartaoService.efetuarTransacao(transacaoDTO));
        assertEquals(ErroTransacao.CARTAO_INEXISTENTE, exception.getErro());

        verify(cartaoRepository, times(1)).findByNumeroCartao(transacaoDTO.getNumeroCartao());
        verify(cartaoRepository, never()).save(any());
    }
}
