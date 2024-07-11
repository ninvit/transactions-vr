package app.model;

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
    private Long numeroCartao;
    private String senha;
    private double saldo = 500.0; // Inicializando o saldo com 500

    public Cartao(String numeroCartao, String senha) {
    }
}