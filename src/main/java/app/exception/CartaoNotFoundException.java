package app.exception;

import app.enums.ErroTransacao;

public class CartaoNotFoundException extends RuntimeException {
    private final ErroTransacao erro;

    public CartaoNotFoundException(ErroTransacao erro) {
        super(erro.name());
        this.erro = erro;
    }

    public ErroTransacao getErro() {
        return erro;
    }
}
