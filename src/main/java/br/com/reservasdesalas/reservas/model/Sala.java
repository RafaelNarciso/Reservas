package br.com.reservasdesalas.reservas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "salas")
@Getter
@Setter
@NoArgsConstructor
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idDaSala;

    @Column(name = "nome_da_sala",
            nullable = false,
            unique = true)
    private String nomeDaSala;

    @Column(nullable = false)
    private int capacidade;

    @Column(nullable = false)
    private boolean ativa = true;

    public Sala(String nomeDaSala, int capacidade) {

        if (capacidade <= 0) {
            throw new IllegalArgumentException(
                    "A capacidade da sala deve ser positiva.");
        }

        this.nomeDaSala = nomeDaSala;
        this.capacidade = capacidade;
    }

    public boolean estaAtiva() {
        return ativa;
    }

    public void desativar() {
        this.ativa = false;
    }

    public void ativar() {
        this.ativa = true;
    }
}
