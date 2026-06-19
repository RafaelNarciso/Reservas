package br.com.reservasdesalas.reservas.controller;

import br.com.reservasdesalas.reservas.model.Sala;
import br.com.reservasdesalas.reservas.service.SalaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SalaController.class)
class SalaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalaService salaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveListarSalas() throws Exception {

        Sala sala = new Sala("Sala Reunião", 10);

        when(salaService.listar()).thenReturn(List.of(sala));

        mockMvc.perform(get("/api/v1/salas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeDaSala").value("Sala Reunião"));
    }

    @Test
    void deveBuscarSalaPorId() throws Exception {

        UUID id = UUID.randomUUID();

        Sala sala = new Sala("Sala 1", 5);
        sala.setIdDaSala(id);

        when(salaService.buscarPorId(id)).thenReturn(sala);

        mockMvc.perform(get("/api/v1/salas/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeDaSala").value("Sala 1"));
    }

    @Test
    void deveCriarSala() throws Exception {

        Sala sala = new Sala("Sala Nova", 15);

        when(salaService.criar(any())).thenReturn(sala);

        mockMvc.perform(post("/api/v1/salas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sala)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeDaSala").value("Sala Nova"));
    }

    @Test
    void deveAtualizarSala() throws Exception {

        UUID id = UUID.randomUUID();

        Sala sala = new Sala("Sala Atualizada", 20);
        sala.setIdDaSala(id);

        when(salaService.atualizar(any(), any())).thenReturn(sala);

        mockMvc.perform(put("/api/v1/salas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sala)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeDaSala").value("Sala Atualizada"));
    }

    @Test
    void deveRemoverSala() throws Exception {

        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/salas/{id}", id))
                .andExpect(status().isNoContent());
    }
}