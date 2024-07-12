package app.service;

import app.dto.TransacaoDTO;
import app.enums.ErroTransacao;
import app.exception.CartaoExistenteException;
import app.exception.CartaoNotFoundException;
import app.model.Cartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import app.repository.CartaoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartaoService {
    @Autowired
    private CartaoRepository cartaoRepository;

    @Transactional
    public void salvarCartao(Cartao cartao) {
        try {
            // Verifica se o cartão já existe
            cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao())
                    .ifPresent(existingCartao -> {
                        throw new CartaoExistenteException(existingCartao);
                    });

            // Salvar o cartão se não existir outro com o mesmo número
            cartao.setSaldo(500.0); // saldo inicial
            cartaoRepository.save(cartao);
        } catch (DataIntegrityViolationException e) {
            // Tratar a exceção de violação de integridade (por exemplo, se a chave única for violada)
            throw new CartaoExistenteException(cartao);
        }
    }
    @Transactional
    public void efetuarTransacao(TransacaoDTO transacaoDTO) {
        Cartao cartao = cartaoRepository.findByNumeroCartao(transacaoDTO.getNumeroCartao())
                .orElseThrow(() -> new CartaoNotFoundException(ErroTransacao.CARTAO_INEXISTENTE));
        cartao.validarSenha(transacaoDTO.getSenha());
        cartao.debitarSaldo(transacaoDTO.getValor());
        cartaoRepository.save(cartao);
    }
}