package br.com.reservasdesalas.reservas.service;

import br.com.reservasdesalas.reservas.enumerated.Status;
import br.com.reservasdesalas.reservas.exception.ConflitoReservaException;
import br.com.reservasdesalas.reservas.exception.RecursoNaoEncontradoException;
import br.com.reservasdesalas.reservas.exception.RegraNegocioException;
import br.com.reservasdesalas.reservas.model.Reserva;
import br.com.reservasdesalas.reservas.repository.ReservasRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
@Service
public class ReservasService {

    private final ReservasRepository reservaRepository;

    public ReservasService(ReservasRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Transactional
    public Reserva criarReserva(Reserva reserva) {

        validarDatas(reserva);

        verificarSalaAtiva(reserva);

        verificarConflitos(reserva);

        return reservaRepository.save(reserva);
    }
    private void validarDatas(Reserva reserva) {

        if (!reserva.getInicio()
                .isBefore(reserva.getFim())) {

            throw new RegraNegocioException(
                    "Início deve ser anterior ao fim");
        }
    }
    private void verificarSalaAtiva(Reserva reserva) {

        if (!reserva.getSala().estaAtiva()) {

            throw new RegraNegocioException(
                    "Sala inativa");
        }
    }

    private void verificarConflitos(Reserva novaReserva) {

        boolean conflito = !reservaRepository.buscarConflitos(
                novaReserva.getSala(),
                Status.CANCELADA,
                novaReserva.getInicio(),
                novaReserva.getFim()
        ).isEmpty();

        if (conflito) {
            throw new ConflitoReservaException(
                    "Sala já reservada nesse horário");
        }
    }

    public List<Reserva> listar() {
        return reservaRepository.findAll();
    }


    public Reserva buscarPorId(UUID id) {
        return reservaRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Reserva não encontrada"));
    }

    @Transactional
    public Reserva cancelarReserva(UUID id) {

        Reserva reserva = buscarPorId(id);

        reserva.cancelar();

        return reservaRepository.save(reserva);
    }


}
