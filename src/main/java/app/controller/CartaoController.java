package app.controller;

import app.dto.TransacaoDTO;
import app.exception.CartaoNotFoundException;
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
        Cartao cartao = cartaoRepository.findByNumeroCartao(transacaoDTO.getNumeroCartao())
                .orElseThrow(() -> new CartaoNotFoundException("Cartão não encontrado."));

        cartao.validarSenha(transacaoDTO.getSenhaCartao());
        cartao.debitarSaldo(transacaoDTO.getValor());
        cartaoRepository.save(cartao);

        return ResponseEntity.ok("Transação realizada com sucesso.");
    }
}
