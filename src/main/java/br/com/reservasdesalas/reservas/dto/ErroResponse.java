package br.com.reservasdesalas.reservas.dto;

public record ErroResponse(
        int status,
        String mensagem
) {
}
