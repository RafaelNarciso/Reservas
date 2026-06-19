package br.com.reservasdesalas.reservas.controller;

import br.com.reservasdesalas.reservas.model.Sala;
import br.com.reservasdesalas.reservas.service.SalaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    public List<Sala> listarSalas() {
        return salaService.listar();
    }

    @GetMapping("/{id}")
    public Sala obterSalaPorId(@PathVariable UUID id) {
        return salaService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sala criar(@RequestBody Sala sala) {
        return salaService.criar(sala);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Sala atualizar(@PathVariable UUID id,
                          @RequestBody Sala sala) {

        return salaService.atualizar(id, sala);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable UUID id) {
        salaService.remover(id);
    }
}