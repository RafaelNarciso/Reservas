package br.com.reservasdesalas.reservas.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegraNegocioExceptionTest {

    @Test
    void deveCriarExceptionComMensagem() {

        RegraNegocioException ex =
                new RegraNegocioException("Erro de regra de negócio");

        assertEquals("Erro de regra de negócio", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void deveCriarExceptionComMensagemECausa() {

        Throwable causa = new RuntimeException("Erro raiz");

        RegraNegocioException ex =
                new RegraNegocioException("Erro de regra", causa);

        assertEquals("Erro de regra", ex.getMessage());
        assertEquals(causa, ex.getCause());
    }
}