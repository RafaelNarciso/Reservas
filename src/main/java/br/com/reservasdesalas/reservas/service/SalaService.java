package br.com.reservasdesalas.reservas.service;

import br.com.reservasdesalas.reservas.exception.RecursoNaoEncontradoException;
import br.com.reservasdesalas.reservas.exception.RegraNegocioException;
import br.com.reservasdesalas.reservas.model.Sala;
import br.com.reservasdesalas.reservas.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SalaService {

    private final SalaRepository repository;

    public SalaService(SalaRepository repository) {
        this.repository = repository;
    }

    public List<Sala> listar() {
        return repository.findAll();
    }

    public Sala buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Sala não encontrada"));
    }

    public Sala criar(Sala sala) {

        validarCapacidade(sala);

        return repository.save(sala);
    }

    public Sala atualizar(UUID id, Sala salaAtualizada) {

        Sala sala = buscarPorId(id);

        validarCapacidade(salaAtualizada);

        sala.setNomeDaSala(salaAtualizada.getNomeDaSala());
        sala.setCapacidade(salaAtualizada.getCapacidade());
        sala.setAtiva(salaAtualizada.estaAtiva());

        return repository.save(sala);
    }

    public void remover(UUID id) {

        Sala sala = buscarPorId(id);

        repository.delete(sala);
    }

    private void validarCapacidade(Sala sala) {

        if (sala.getCapacidade() <= 0) {
            throw new RegraNegocioException(
                    "Capacidade deve ser positiva");
        }
    }
}
