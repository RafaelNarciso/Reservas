package br.com.reservasdesalas.reservas.service;

import br.com.reservasdesalas.reservas.exception.RecursoNaoEncontradoException;
import br.com.reservasdesalas.reservas.model.Sala;
import br.com.reservasdesalas.reservas.repository.SalaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {

    @Mock
    private SalaRepository salaRepository;

    @InjectMocks
    private SalaService salaService;

    private Sala sala;
    private UUID id;

    @BeforeEach
    void setUp() {

        id = UUID.randomUUID();

        sala = new Sala("Sala Reunião", 10);
        sala.setIdDaSala(id);
    }

    @Test
    void deveListarSalas() {

        when(salaRepository.findAll()).thenReturn(List.of(sala));

        List<Sala> resultado = salaService.listar();

        assertEquals(1, resultado.size());
        assertEquals(id, resultado.get(0).getIdDaSala());

        verify(salaRepository).findAll();
    }

    @Test
    void deveBuscarSalaPorId() {

        when(salaRepository.findById(id)).thenReturn(Optional.of(sala));

        Sala resultado = salaService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdDaSala());

        verify(salaRepository).findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoSalaNaoExistir() {

        when(salaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> salaService.buscarPorId(id));
    }


    @Test
    void deveCriarSalaComSucesso() {

        when(salaRepository.save(any(Sala.class))).thenReturn(sala);

        Sala resultado = salaService.criar(sala);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdDaSala());

        verify(salaRepository).save(sala);
    }


    @Test
    void deveAtualizarSalaComSucesso() {

        Sala atualizada = new Sala("Sala Atualizada", 20);
        atualizada.setIdDaSala(id);

        when(salaRepository.findById(id)).thenReturn(Optional.of(sala));
        when(salaRepository.save(any(Sala.class))).thenReturn(atualizada);

        Sala resultado = salaService.atualizar(id, atualizada);

        assertEquals("Sala Atualizada", resultado.getNomeDaSala());
        assertEquals(20, resultado.getCapacidade());

        verify(salaRepository).save(any(Sala.class));
    }

    @Test
    void deveLancarExcecaoAoAtualizarSalaInexistente() {

        when(salaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> salaService.atualizar(id, sala));
    }


    @Test
    void deveRemoverSalaComSucesso() {

        when(salaRepository.findById(id)).thenReturn(Optional.of(sala));
        doNothing().when(salaRepository).delete(sala);

        salaService.remover(id);

        verify(salaRepository).delete(sala);
    }

    @Test
    void deveLancarExcecaoAoRemoverSalaInexistente() {

        when(salaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> salaService.remover(id));
    }
}