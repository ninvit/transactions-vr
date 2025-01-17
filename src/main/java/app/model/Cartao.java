package app.model;

import app.enums.ErroTransacao;
import app.exception.SaldoInsuficienteException;
import app.exception.SenhaIncorretaException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cartao {
    @Id
    @GeneratedValue
    private Long id;
    private String numeroCartao;
    private String senha;
    private double saldo = 500.0; // Inicializando o saldo com 500

    public void validarSenha(String senhaCartao) {
        Optional.of(this.senha)
                .filter(s -> s.equals(senhaCartao))
                .orElseThrow(() -> new SenhaIncorretaException(ErroTransacao.SENHA_INVALIDA));
    }

    public void debitarSaldo(double valor) {
        Optional.of(this.saldo)
                .filter(s -> s >= valor)
                .ifPresentOrElse(
                        s -> this.saldo -= valor,
                        () -> { throw new SaldoInsuficienteException(ErroTransacao.SALDO_INSUFICIENTE); }
                );
    }
}