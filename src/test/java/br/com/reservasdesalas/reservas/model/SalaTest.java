package br.com.reservasdesalas.reservas.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalaTest {

    private Sala sala;

    @BeforeEach
    void setUp() {
        sala = new Sala();
        sala.setNomeDaSala("Sala Reunião");
        sala.setCapacidade(10);
        sala.setAtiva(true);
    }

    @Test
    void deveRetornarTrueQuandoSalaEstiverAtiva() {

        assertTrue(sala.estaAtiva());
    }

    @Test
    void deveDesativarSala() {

        sala.desativar();

        assertFalse(sala.estaAtiva());
    }

    @Test
    void deveAtivarSala() {

        sala.setAtiva(false);

        sala.ativar();

        assertTrue(sala.estaAtiva());
    }

    @Test
    void deveLancarExcecaoQuandoCapacidadeForMenorOuIgualAZero() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new Sala("Sala Teste", 0)
                );

        assertEquals(
                "A capacidade da sala deve ser positiva.",
                exception.getMessage()
        );
    }

    @Test
    void deveCriarSalaComCapacidadeValida() {

        Sala sala = new Sala();
        sala.setNomeDaSala("Sala Grande");
        sala.setCapacidade(20);
        sala.setAtiva(true);

        assertEquals("Sala Grande", sala.getNomeDaSala());
        assertEquals(20, sala.getCapacidade());
        assertTrue(sala.estaAtiva());
    }
}