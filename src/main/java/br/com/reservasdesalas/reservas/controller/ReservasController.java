package br.com.reservasdesalas.reservas.controller;

import br.com.reservasdesalas.reservas.model.Reserva;
import br.com.reservasdesalas.reservas.service.ReservasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservasController {

    private final ReservasService reservasService;

    public ReservasController(ReservasService reservasService) {
        this.reservasService = reservasService;
    }

    @GetMapping
    public List<Reserva> listar() {
        return reservasService.listar();
    }

    @GetMapping("/{id}")
    public Reserva buscar(@PathVariable UUID id) {
        return reservasService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva criar(@RequestBody Reserva reserva) {
        return reservasService.criarReserva(reserva);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable UUID id) {
        reservasService.cancelarReserva(id);
    }
}
