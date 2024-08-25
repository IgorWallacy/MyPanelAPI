package com.doks.mypanel.controller;

import com.doks.mypanel.model.Cliente;
import com.doks.mypanel.model.dto.ClienteDto;
import com.doks.mypanel.repository.ClienteRepository;
import com.doks.mypanel.service.ClienteService;
import org.apache.commons.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private  ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/todos/{id}")
    private ResponseEntity<?> listarClientes(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.todosClientesPorVendedor(id));
    }

    @PostMapping("/cadastrar")
    private @NotNull ResponseEntity<?> cadastrarCliente(@NotNull @RequestBody Cliente cliente){

        return ResponseEntity.ok(clienteService.cadastrarCliente(cliente));
    }

    @PutMapping("/atualizar")
    private @NotNull ResponseEntity<?> atualizarCliente(@NotNull @RequestBody Cliente cliente){



        return ResponseEntity.ok(clienteService.atualizarCliente(cliente));
    }

}
