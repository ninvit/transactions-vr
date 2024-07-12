package app.model;

import app.exception.SaldoInsuficienteException;
import app.exception.SenhaIncorretaException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cartao {
    @Id
    @GeneratedValue
    private Long id;
    private Long numeroCartao;
    private String senha;
    private double saldo = 500.0; // Inicializando o saldo com 500

    public void validarSenha(String senhaCartao) {
        if (!this.senha.equals(senhaCartao)) {
            throw new SenhaIncorretaException("SENHA_INVALIDA");
        }
    }

    public void debitarSaldo(double valor) {
        if (this.saldo < valor) {
            throw new SaldoInsuficienteException("SALDO_INSUFICIENTE");
        }
        this.saldo -= valor;
    }
}