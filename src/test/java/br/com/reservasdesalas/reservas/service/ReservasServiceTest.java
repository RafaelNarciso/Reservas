package br.com.reservasdesalas.reservas.service;

import br.com.reservasdesalas.reservas.enumerated.Status;
import br.com.reservasdesalas.reservas.exception.ConflitoReservaException;
import br.com.reservasdesalas.reservas.exception.RecursoNaoEncontradoException;
import br.com.reservasdesalas.reservas.exception.RegraNegocioException;
import br.com.reservasdesalas.reservas.model.Reserva;
import br.com.reservasdesalas.reservas.model.Sala;
import br.com.reservasdesalas.reservas.model.Usuario;
import br.com.reservasdesalas.reservas.repository.ReservasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservasServiceTest {

    @Mock
    private ReservasRepository reservasRepository;

    @InjectMocks
    private ReservasService reservasService;

    private Reserva reserva;

    private UUID id;

    @BeforeEach
    void setUp() {

        id = UUID.randomUUID();

        Sala sala = new Sala("Sala Reunião", 10);

        Usuario usuario = new Usuario();
        usuario.setNome("Rafael");

        reserva = new Reserva();

        reserva.setId(id);

        reserva.setSala(sala);

        reserva.setUsuario(usuario);

        reserva.setInicio(LocalDateTime.of(2026, 6, 20, 9, 0));

        reserva.setFim(LocalDateTime.of(2026, 6, 20, 10, 0));

        reserva.setStatus(Status.ATIVA);
    }

    @Test
    void deveCriarReservaComSucesso() {

        when(reservasRepository.buscarConflitos(
                reserva.getSala(),
                Status.CANCELADA,
                reserva.getInicio(),
                reserva.getFim()
        )).thenReturn(List.of());

        when(reservasRepository.save(reserva)).thenReturn(reserva);

        Reserva resultado = reservasService.criarReserva(reserva);

        assertNotNull(resultado);

        assertEquals(id, resultado.getId());

        verify(reservasRepository).save(reserva);
    }

    @Test
    void deveLancarExcecaoQuandoSalaEstiverInativa() {

        reserva.getSala().desativar();

        assertThrows(RegraNegocioException.class, () -> reservasService.criarReserva(reserva));

        verify(reservasRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoHouverConflito() {

        Reserva existente = new Reserva();

        existente.setInicio(LocalDateTime.of(2026, 6, 20, 9, 30));

        existente.setFim(LocalDateTime.of(2026, 6, 20, 10, 30));
        existente.setStatus(Status.ATIVA);

        when(reservasRepository.buscarConflitos(
                reserva.getSala(),
                Status.CANCELADA,
                reserva.getInicio(),
                reserva.getFim()
        )).thenReturn(List.of(existente));

        assertThrows(ConflitoReservaException.class, () -> reservasService.criarReserva(reserva));

        verify(reservasRepository, never()).save(any());
    }

    @Test
    void deveListarReservas() {

        when(reservasRepository.findAll()).thenReturn(List.of(reserva));

        List<Reserva> reservas = reservasService.listar();

        assertEquals(1, reservas.size());

        verify(reservasRepository).findAll();
    }

    @Test
    void deveBuscarReservaPorId() {

        when(reservasRepository.findById(id)).thenReturn(Optional.of(reserva));

        Reserva resultado = reservasService.buscarPorId(id);

        assertEquals(id, resultado.getId());

        verify(reservasRepository).findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoReservaNaoExistir() {

        when(reservasRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> reservasService.buscarPorId(id));
    }

    @Test
    void deveCancelarReserva() {

        when(reservasRepository.findById(id)).thenReturn(Optional.of(reserva));

        when(reservasRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Reserva resultado = reservasService.cancelarReserva(id);

        assertEquals(Status.CANCELADA, resultado.getStatus());

        verify(reservasRepository).save(reserva);
    }


}