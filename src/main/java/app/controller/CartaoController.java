package app.controller;

import app.model.Cartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.repository.CartaoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @GetMapping
    public List<Cartao> buscarTodosCartoes() {
        return cartaoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cartao> buscarPorId(@PathVariable Long id) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        return cartaoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cartao> salvarCartao(@RequestBody Cartao cartao) {
        // Definindo saldo inicial como 500
        cartao.setSaldo(500.0);
        Cartao novoCartao = cartaoRepository.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCartao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cartao> atualizarCartao(@PathVariable Long id, @RequestBody Cartao cartaoAtualizado) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if (cartaoOptional.isPresent()) {
            Cartao cartaoExistente = cartaoOptional.get();
            cartaoExistente.setNumeroCartao(cartaoAtualizado.getNumeroCartao());
            cartaoExistente.setSenha(cartaoAtualizado.getSenha());
            Cartao cartaoAtualizadoDb = cartaoRepository.save(cartaoExistente);
            return ResponseEntity.ok(cartaoAtualizadoDb);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCartao(@PathVariable Long id) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if (cartaoOptional.isPresent()) {
            cartaoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
