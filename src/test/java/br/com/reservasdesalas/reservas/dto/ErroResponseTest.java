package br.com.reservasdesalas.reservas.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErroResponseTest {

    @Test
    void deveCriarErroResponseCorretamente() {

        ErroResponse erro = new ErroResponse(400, "Erro de validação");

        assertEquals(400, erro.status());
        assertEquals("Erro de validação", erro.mensagem());
    }

    @Test
    void deveSerIgualQuandoValoresForemIguais() {

        ErroResponse e1 = new ErroResponse(400, "Erro");
        ErroResponse e2 = new ErroResponse(400, "Erro");

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}