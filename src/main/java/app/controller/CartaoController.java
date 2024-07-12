package app.controller;

import app.dto.TransacaoDTO;
import app.exception.CartaoExistenteException;
import app.model.Cartao;
import app.service.CartaoService;
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
    @Autowired
    private CartaoService cartaoService;
    @GetMapping
    public List<Cartao> buscarTodosCartoes() {
        return cartaoRepository.findAll();
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<Cartao> buscarSaldoPorNumeroCartao(@PathVariable Long numeroCartao) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findByNumeroCartao(numeroCartao);
        return cartaoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> salvarCartao(@RequestBody Cartao cartao) {
        try {
            cartaoService.salvarCartao(cartao);
            return ResponseEntity.status(HttpStatus.CREATED).body(cartao);
        } catch (CartaoExistenteException e) {
            // Cartão já existe, retornar Status Code 422 com os detalhes do cartão existente
            Cartao cartaoExistente = e.getCartaoExistente();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(String.format("{\"senha\": \"%s\", \"numeroCartao\": \"%s\"}",
                            cartaoExistente.getSenha(), cartaoExistente.getNumeroCartao()));
        }
    }

    @PutMapping("/transacao")
    public ResponseEntity<String> efetuarTransacao(@RequestBody TransacaoDTO transacaoDTO) {
        cartaoService.efetuarTransacao(transacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }
}
