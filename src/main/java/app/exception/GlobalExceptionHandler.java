package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartaoNotFoundException.class)
    public ResponseEntity<String> handleCartaoNotFoundException(CartaoNotFoundException ex) {
        return new ResponseEntity<>("CARTAO_INEXISTENTE", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(SenhaIncorretaException.class)
    public ResponseEntity<String> handleSenhaIncorretaException(SenhaIncorretaException ex) {
        return new ResponseEntity<>("SENHA_INVALIDA", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<String> handleSaldoInsuficienteException(SaldoInsuficienteException ex) {
        return new ResponseEntity<>("SALDO_INSUFICIENTE", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
