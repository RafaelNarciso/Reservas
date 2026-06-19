package br.com.reservasdesalas.reservas.service;

import br.com.reservasdesalas.reservas.exception.RecursoNaoEncontradoException;
import br.com.reservasdesalas.reservas.model.Usuario;
import br.com.reservasdesalas.reservas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Usuário não encontrado"));
    }

    public Usuario criar(Usuario usuario) {
        return repository.save(usuario);
    }

    public Usuario atualizar(UUID id, Usuario usuarioAtualizado) {

        Usuario usuario = buscarPorId(id);

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setCpf(usuarioAtualizado.getCpf());

        return repository.save(usuario);
    }

    public void remover(UUID id) {

        Usuario usuario = buscarPorId(id);

        repository.delete(usuario);
    }
}
