package app.exception;

import app.enums.ErroTransacao;
import app.model.Cartao;

public class CartaoExistenteException extends RuntimeException {

    private final Cartao cartaoExistente;

    public CartaoExistenteException(Cartao cartaoExistente) {
        super(ErroTransacao.CARTAO_EXISTENTE.name());
        this.cartaoExistente = cartaoExistente;
    }

    public Cartao getCartaoExistente() {
        return cartaoExistente;
    }
}
