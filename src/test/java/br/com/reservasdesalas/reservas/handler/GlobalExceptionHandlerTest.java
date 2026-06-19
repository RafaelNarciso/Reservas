package br.com.reservasdesalas.reservas.handler;

import br.com.reservasdesalas.reservas.exception.RegraNegocioException;
import br.com.reservasdesalas.reservas.exception.RecursoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void deveTratarRegraDeNegocio() {

        RegraNegocioException ex =
                new RegraNegocioException("Regra inválida");

        ResponseEntity<?> response = handler.tratarRegra(ex);

        assertNotNull(response);
        assertEquals(400, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void deveTratarRecursoNaoEncontrado() {

        RecursoNaoEncontradoException ex =
                new RecursoNaoEncontradoException("Não encontrado");

        ResponseEntity<?> response = handler.tratar404(ex);

        assertNotNull(response);
        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }
}