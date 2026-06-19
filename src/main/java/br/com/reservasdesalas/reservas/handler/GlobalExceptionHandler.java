package br.com.reservasdesalas.reservas.handler;

import br.com.reservasdesalas.reservas.dto.ErroResponse;
import br.com.reservasdesalas.reservas.exception.RecursoNaoEncontradoException;
import br.com.reservasdesalas.reservas.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResponse> tratarRegra(
            RegraNegocioException ex) {

        return ResponseEntity.badRequest()
                .body(new ErroResponse(
                        400,
                        ex.getMessage()));
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> tratar404(
            RecursoNaoEncontradoException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(
                        404,
                        ex.getMessage()));
    }
}
