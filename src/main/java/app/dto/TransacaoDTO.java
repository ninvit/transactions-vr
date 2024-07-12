package app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTO {
    private Long numeroCartao;
    private String senha;
    private double valor;
}
