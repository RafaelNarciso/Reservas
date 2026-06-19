package br.com.reservasdesalas.reservas.exception;

public class RecursoNaoEncontradoException
        extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}