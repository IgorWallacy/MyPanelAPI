package com.doks.mypanel.controller;

import com.doks.mypanel.exception.UsuarioEmailJaExisteException;
import com.doks.mypanel.exception.UsuarioNotFoundException;
import com.doks.mypanel.model.Usuario;
import com.doks.mypanel.model.dto.UsuarioDto;
import com.doks.mypanel.repository.UsuarioRepository;
import com.doks.mypanel.service.UsuarioService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;




    @PostMapping("/novo")
    public ResponseEntity<?> novoUsuario(@RequestBody @NotNull Usuario usuario) {
        if (repository.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new UsuarioEmailJaExisteException("E-mail não está disponível para cadastro! "));
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario savedUsuario = repository.save(usuario);

        return ResponseEntity.ok(savedUsuario);
    }


    @PutMapping("/perfil/atualizar")
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody @NotNull Usuario usuario) {

        Usuario usuarioSalvo = repository.findByEmail(usuario.getEmail())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado"));


        usuarioSalvo.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return ResponseEntity.ok(repository.save(usuarioSalvo));

    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarDados(@NotNull @RequestBody Usuario usuario) {

        Usuario usuarioSalvo = repository.findById(usuario.getId())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado"));




        // Check if the email is already registered by another user
        Optional<Usuario> usuarioExistente = repository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(usuario.getId())) {
            return ResponseEntity.badRequest().body(new UsuarioEmailJaExisteException("E-mail não está disponível para cadastro! "));
        }

        usuarioSalvo.setNome(usuario.getNome());
        usuarioSalvo.setEmail(usuario.getEmail());


        return ResponseEntity.ok(repository.save(usuarioSalvo));

    }

    @GetMapping("/todos")
    public ResponseEntity<List<UsuarioDto>> todosUsuarios() {

        return ResponseEntity.ok(usuarioService.buscarTodos());
    }


    @GetMapping("/{id}/subusuarios")
    public List<Usuario> listarSubUsuarios(@PathVariable Long id) {
        return usuarioService.getFilhos(id);
    }

    @GetMapping("/{id}/pai-e-descendentes")
    public ResponseEntity<List<UsuarioDto>> getUsuarioPaiEDescendentes(@PathVariable Long id) {


        return ResponseEntity.ok(usuarioService.getUsuarioPaiEDescendentes(id));
    }


}
