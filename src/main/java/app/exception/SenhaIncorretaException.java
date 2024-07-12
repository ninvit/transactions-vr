package app.exception;

import app.enums.ErroTransacao;

public class SenhaIncorretaException extends RuntimeException {
    private final ErroTransacao erro;

    public SenhaIncorretaException(ErroTransacao erro) {
        super(erro.name());
        this.erro = erro;
    }

    public ErroTransacao getErro() {
        return erro;
    }
}
