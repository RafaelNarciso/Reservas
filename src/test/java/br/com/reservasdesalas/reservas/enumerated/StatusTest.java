package br.com.reservasdesalas.reservas.enumerated;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {
    @Test
    void deveConterStatusPrincipais() {

        Status[] valores = Status.values();

        assertTrue(contains(valores, Status.ATIVA));
        assertTrue(contains(valores, Status.CANCELADA));
    }

    @Test
    void devePermitirBuscarPorNome() {

        assertEquals(Status.ATIVA, Status.valueOf("ATIVA"));
        assertEquals(Status.CANCELADA, Status.valueOf("CANCELADA"));
    }

    private boolean contains(Status[] valores, Status status) {
        for (Status s : valores) {
            if (s == status) return true;
        }
        return false;
    }
}