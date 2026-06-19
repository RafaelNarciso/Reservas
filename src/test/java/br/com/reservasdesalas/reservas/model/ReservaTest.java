package br.com.reservasdesalas.reservas.model;

import br.com.reservasdesalas.reservas.enumerated.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    private Reserva reserva;

    @BeforeEach
    void setUp() {

        reserva = new Reserva();

        reserva.setInicio(LocalDateTime.of(2025, 6, 20, 9, 0));

        reserva.setFim(LocalDateTime.of(2025, 6, 20, 10, 0));

        reserva.setStatus(Status.ATIVA);
    }

    @Test
    void deveRetornarTrueQuandoReservasConflitam() {

        Reserva outra = new Reserva();

        outra.setInicio(LocalDateTime.of(2025, 6, 20, 9, 30));

        outra.setFim(LocalDateTime.of(2025, 6, 20, 10, 30));

        outra.setStatus(Status.ATIVA);

        assertTrue(reserva.conflitaCom(outra));
    }

    @Test
    void naoDeveConflitarQuandoHorariosNaoSobrepoem() {

        Reserva outra = new Reserva();

        outra.setInicio(LocalDateTime.of(2025, 6, 20, 10, 0));

        outra.setFim(LocalDateTime.of(2025, 6, 20, 11, 0));

        outra.setStatus(Status.ATIVA);

        assertFalse(reserva.conflitaCom(outra));
    }
    @Test
    void reservasCanceladasNaoDevemGerarConflito() {

        Reserva outra = new Reserva();

        outra.setInicio(LocalDateTime.of(2025, 6, 20, 9, 30));

        outra.setFim(LocalDateTime.of(2025, 6, 20, 10, 30));

        outra.setStatus(Status.CANCELADA);

        assertFalse(reserva.conflitaCom(outra));
    }
    @Test
    void deveCancelarReserva() {

        reserva.cancelar();

        assertEquals(Status.CANCELADA, reserva.getStatus());
    }

    @Test
    void deveLancarExcecaoAoCancelarReservaJaCancelada() {

        reserva.cancelar();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> reserva.cancelar());

        assertEquals("Reserva já cancelada", exception.getMessage());
    }

}