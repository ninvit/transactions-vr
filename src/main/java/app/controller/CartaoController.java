package app.controller;

import app.dto.TransacaoDTO;
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
    public ResponseEntity<Cartao> buscarSaldoPorNumeroCartao(@PathVariable Long id) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        return cartaoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cartao> salvarCartao(@RequestBody Cartao cartao) {
        Cartao novoCartao = cartaoRepository.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCartao);
    }

    @PutMapping
    public ResponseEntity<Cartao> efetuarTransacao(@RequestBody Cartao cartao) {
        Cartao novoCartao = cartaoRepository.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCartao);
    }

    @PostMapping("/transacao")
    public ResponseEntity<String> efetuarTransacao(@RequestBody TransacaoDTO transacaoDTO) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findByNumeroCartao(transacaoDTO.getNumeroCartao());
        if (cartaoOptional.isPresent()) {
            Cartao cartao = cartaoOptional.get();
            if (cartao.getSenha().equals(transacaoDTO.getSenhaCartao())) {
                if (cartao.getSaldo() >= transacaoDTO.getValor()) {
                    cartao.setSaldo(cartao.getSaldo() - transacaoDTO.getValor());
                    cartaoRepository.save(cartao);
                    return ResponseEntity.ok("Transação realizada com sucesso.");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo insuficiente.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão não encontrado.");
        }
    }
}
