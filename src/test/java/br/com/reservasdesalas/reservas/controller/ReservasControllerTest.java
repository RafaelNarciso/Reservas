package br.com.reservasdesalas.reservas.controller;

import br.com.reservasdesalas.reservas.model.Reserva;
import br.com.reservasdesalas.reservas.enumerated.Status;
import br.com.reservasdesalas.reservas.service.ReservasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservasController.class)
class ReservasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservasService reservasService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar() throws Exception {

        Reserva reserva = criarReserva();

        when(reservasService.listar()).thenReturn(List.of(reserva));

        mockMvc.perform(get("/api/v1/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("ATIVA"));
    }

    @Test
    void buscar() throws Exception {

        UUID id = UUID.randomUUID();
        Reserva reserva = criarReserva();

        when(reservasService.buscarPorId(id)).thenReturn(reserva);

        mockMvc.perform(get("/api/v1/reservas/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ATIVA"));
    }

    @Test
    void criar() throws Exception {

        Reserva reserva = criarReserva();

        when(reservasService.criarReserva(any())).thenReturn(reserva);

        mockMvc.perform(post("/api/v1/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("ATIVA"));
    }

    @Test
    void cancelar() throws Exception {

        UUID id = UUID.randomUUID();
        Reserva reserva = criarReserva();
        reserva.setStatus(Status.CANCELADA);

        when(reservasService.cancelarReserva(id)).thenReturn(reserva);

        mockMvc.perform(delete("/api/v1/reservas/{id}", id))
                .andExpect(status().isNoContent());
    }

    private Reserva criarReserva() {

        Reserva reserva = new Reserva();

        reserva.setId(UUID.randomUUID());
        reserva.setInicio(LocalDateTime.now());
        reserva.setFim(LocalDateTime.now().plusHours(1));
        reserva.setStatus(Status.ATIVA);

        return reserva;
    }
}