package app.service;

import app.model.Cartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.repository.CartaoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    @Autowired
    public CartaoService(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public Cartao salvarCartao(Cartao cartao) {
        cartao.setSaldo(500.0);
        return cartaoRepository.save(cartao);
    }

    public List<Cartao> buscarTodosCartoes() {
        return cartaoRepository.findAll();
    }

    public Cartao buscarPorId(Long id) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        return cartaoOptional.orElse(null);
    }

    public Cartao atualizarCartao(Long id, Cartao cartaoAtualizado) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if (cartaoOptional.isPresent()) {
            Cartao cartaoExistente = cartaoOptional.get();
            cartaoExistente.setNumeroCartao(cartaoAtualizado.getNumeroCartao());
            cartaoExistente.setSenha(cartaoAtualizado.getSenha());
            return cartaoRepository.save(cartaoExistente);
        } else {
            return null;
        }
    }

    public void deletarCartao(Long id) {
        cartaoRepository.deleteById(id);
    }
}