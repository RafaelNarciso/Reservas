package br.com.reservasdesalas.reservas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idUsuario;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    private String email;

    public Usuario(UUID idUsuario, String nome, String cpf, String email) {
         if(nome == null || nome.isEmpty()){
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        if(cpf == null || cpf.isEmpty()){
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

}


