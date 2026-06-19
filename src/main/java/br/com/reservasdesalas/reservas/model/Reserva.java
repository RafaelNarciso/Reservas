package br.com.reservasdesalas.reservas.model;

import br.com.reservasdesalas.reservas.enumerated.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDateTime dataCriacao;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sala_id")
    private Sala sala;

    public Reserva(UUID id, LocalDateTime inicio, LocalDateTime fim, Usuario usuario, Sala sala) {

        if (!sala.estaAtiva()) {
            throw new IllegalArgumentException("Sala inativa");
        }

        if (!inicio.isBefore(fim)) {
            throw new IllegalArgumentException("Data inicial deve ser antes da final");
        }
        this.id = id;
        this.dataCriacao = LocalDateTime.now();
        this.inicio = inicio;
        this.fim = fim;
        this.usuario = usuario;
        this.sala = sala;
        this.status = Status.ATIVA;
    }


    public boolean conflitaCom(Reserva outra) {

        if (this.status == Status.CANCELADA ||
                outra.status == Status.CANCELADA) {
            return false;
        }
        return this.inicio.isBefore(outra.fim)
                && outra.inicio.isBefore(this.fim);
    }

    public void cancelar() {
        if (status == Status.CANCELADA) {
            throw new IllegalStateException(
                    "Reserva já cancelada");
        }
        status = Status.CANCELADA;
    }


}
