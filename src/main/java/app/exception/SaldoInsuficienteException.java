package app.exception;

import app.enums.ErroTransacao;

public class SaldoInsuficienteException extends RuntimeException {
    private final ErroTransacao erro;

    public SaldoInsuficienteException(ErroTransacao erro) {
        super(erro.name());
        this.erro = erro;
    }

    public ErroTransacao getErro() {
        return erro;
    }
}
