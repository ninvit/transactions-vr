package app.model;

import app.exception.SaldoInsuficienteException;
import app.exception.SenhaIncorretaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CartaoTest {

    @Test
    public void testValidarSenhaCorreta() {
        Cartao cartao = new Cartao(1L, 123456789L, "senha1234", 1000.0);
        cartao.validarSenha("senha1234");
    }

    @Test
    public void testValidarSenhaIncorreta() {
        Cartao cartao = new Cartao(1L, 123456789L, "senha1234", 1000.0);
        assertThrows(SenhaIncorretaException.class, () -> cartao.validarSenha("senhaErrada"));
    }

    @Test
    public void testDebitarSaldoSuficiente() {
        Cartao cartao = new Cartao(1L, 123456789L, "senha1234", 1000.0);
        cartao.debitarSaldo(500.0);
        assertEquals(500.0, cartao.getSaldo());
    }

    @Test
    public void testDebitarSaldoInsuficiente() {
        Cartao cartao = new Cartao(1L, 123456789L, "senha1234", 1000.0);
        assertThrows(SaldoInsuficienteException.class, () -> cartao.debitarSaldo(1500.0));
    }
}

