package com.doks.mypanel.service;

import com.doks.mypanel.model.Cliente;
import com.doks.mypanel.model.dto.ClienteDto;
import com.doks.mypanel.repository.ClienteRepository;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.codec.binary.Base64;

import java.util.List;
import java.util.Optional;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;




    public List<ClienteDto> todosClientesPorVendedor(Long id) {

        return repository.todosClientesPorVendedor(id);
    }

    public Cliente cadastrarCliente(Cliente cliente) {



        repository.salvar(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getWhatsapp(), cliente.getFoto(), cliente.getObservacao() , cliente.getUsuario().getId());
        return cliente;
    }

    public Cliente atualizarCliente(@NotNull Cliente cliente) {
        repository.atualizar(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getWhatsapp(), cliente.getFoto(), cliente.getObservacao() , cliente.getUsuario().getId());
        return cliente;
    }


}
