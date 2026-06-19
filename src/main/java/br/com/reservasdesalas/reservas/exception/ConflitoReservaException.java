package br.com.reservasdesalas.reservas.exception;

public class ConflitoReservaException extends RuntimeException {

    public ConflitoReservaException(String mensagem) {
        super(mensagem);
    }

    public ConflitoReservaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
