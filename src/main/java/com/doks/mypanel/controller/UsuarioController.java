package com.doks.mypanel.controller;

import com.doks.mypanel.exception.UsuarioNotFoundException;
import com.doks.mypanel.model.Usuario;
import com.doks.mypanel.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PutMapping("/perfil/atualizar")
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario) {

        Usuario usuarioSalvo = repository.findByEmail(usuario.getEmail())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado"));


        usuarioSalvo.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return ResponseEntity.ok(repository.save(usuarioSalvo));

 }

}
