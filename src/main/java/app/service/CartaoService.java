package app.service;

import app.dto.TransacaoDTO;
import app.enums.ErroTransacao;
import app.exception.CartaoNotFoundException;
import app.model.Cartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.repository.CartaoRepository;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CartaoService {
    @Autowired
    private CartaoRepository cartaoRepository;

    @Transactional
    public void efetuarTransacao(TransacaoDTO transacaoDTO) {
        Cartao cartao = cartaoRepository.findByNumeroCartao(transacaoDTO.getNumeroCartao())
                .orElseThrow(() -> new CartaoNotFoundException(ErroTransacao.CARTAO_INEXISTENTE));
        cartao.validarSenha(transacaoDTO.getSenha());
        cartao.debitarSaldo(transacaoDTO.getValor());
        cartaoRepository.save(cartao);
    }
}