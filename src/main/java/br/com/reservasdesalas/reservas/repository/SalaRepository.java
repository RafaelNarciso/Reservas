package br.com.reservasdesalas.reservas.repository;

import br.com.reservasdesalas.reservas.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SalaRepository extends JpaRepository<Sala, UUID> {
    boolean existsByNomeDaSala(String nomeDaSala);
}
