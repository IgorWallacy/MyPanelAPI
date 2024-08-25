package com.doks.mypanel.controller;

import com.doks.mypanel.model.Login;
import com.doks.mypanel.repository.LoginRepository;
import com.doks.mypanel.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> login(@RequestBody Login login) {

        return ResponseEntity.ok(service.salvarOuAtualizar(login));
    }

    @PostMapping("/atualizar")
    public ResponseEntity<?> atualizar(@RequestBody Login login) {

        return ResponseEntity.ok(service.atualizar(login));
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listar(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarTodos(id));
    }

    @GetMapping("/listar/ativos/{id}")
    public ResponseEntity<?> listarAtivosPorVendedorLogado(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarTodosAtivos(id));
    }
}
