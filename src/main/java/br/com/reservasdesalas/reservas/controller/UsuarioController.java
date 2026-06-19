package br.com.reservasdesalas.reservas.controller;

import br.com.reservasdesalas.reservas.model.Usuario;
import br.com.reservasdesalas.reservas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    @GetMapping("/{id}")
    public Usuario buscar(@PathVariable UUID id) {
        return usuarioService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario criar(@RequestBody Usuario usuario) {
        return usuarioService.criar(usuario);
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable UUID id,
                             @RequestBody Usuario usuario) {

        return usuarioService.atualizar(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable UUID id) {

        usuarioService.remover(id);
    }
}
