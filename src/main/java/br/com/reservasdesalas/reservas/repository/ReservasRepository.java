package br.com.reservasdesalas.reservas.repository;

import br.com.reservasdesalas.reservas.enumerated.Status;
import br.com.reservasdesalas.reservas.model.Reserva;
import br.com.reservasdesalas.reservas.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservasRepository extends JpaRepository<Reserva, UUID> {
    List<Reserva> findBySala(Sala sala);
    List<Reserva> findBySalaAndStatusNot(Sala sala, Status status);
    @Query("""
        SELECT r
        FROM Reserva r
        WHERE r.sala = :sala
        AND r.status <> :status
        AND r.inicio < :fim
        AND r.fim > :inicio
    """)
    List<Reserva> buscarConflitos(
            @Param("sala") Sala sala,
            @Param("status") Status status,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
}
