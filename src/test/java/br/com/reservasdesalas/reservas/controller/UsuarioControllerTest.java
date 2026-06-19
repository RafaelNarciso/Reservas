package br.com.reservasdesalas.reservas.controller;

import br.com.reservasdesalas.reservas.model.Usuario;
import br.com.reservasdesalas.reservas.service.UsuarioService;
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

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void deveListarUsuarios() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(UUID.randomUUID());
        usuario.setNome("Rafael");

        when(usuarioService.listar()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Rafael"));
    }

    @Test
    void deveBuscarUsuarioPorId() throws Exception {

        UUID id = UUID.randomUUID();

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);
        usuario.setNome("Rafael");

        when(usuarioService.buscarPorId(id)).thenReturn(usuario);

        mockMvc.perform(get("/api/v1/usuarios/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Rafael"));
    }

    @Test
    void deveCriarUsuario() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setNome("Rafael");

        when(usuarioService.criar(any())).thenReturn(usuario);

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Rafael"));
    }

    @Test
    void deveRemoverUsuario() throws Exception {

        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/usuarios/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void deveAtualizarUsuario() throws Exception {

        UUID id = UUID.randomUUID();

        Usuario usuario = new Usuario();
        usuario.setNome("Atualizado");

        when(usuarioService.atualizar(any(), any())).thenReturn(usuario);

        mockMvc.perform(put("/api/v1/usuarios/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Atualizado"));
    }
}