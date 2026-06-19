package br.com.reservasdesalas.reservas.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ConflitoReservaExceptionTest extends RuntimeException {
    @Test
    void deveCriarExceptionComMensagem() {

        ConflitoReservaException ex =
                new ConflitoReservaException("Conflito de horário");

        assertEquals("Conflito de horário", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void deveCriarExceptionComMensagemECausa() {

        Throwable causa = new RuntimeException("Erro raiz");

        ConflitoReservaException ex =
                new ConflitoReservaException("Conflito de horário", causa);

        assertEquals("Conflito de horário", ex.getMessage());
        assertEquals(causa, ex.getCause());
    }
}