package com.doks.mypanel.service;

import com.doks.mypanel.model.Login;
import com.doks.mypanel.model.dto.LoginDto;
import com.doks.mypanel.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private LoginRepository repository;

    public Login salvarOuAtualizar(Login login) {
        return repository.save(login);
    }

    public Login atualizar(Login login) {

        login.setDataAlteracao(LocalDateTime.now());
        repository.atualizar(login.getId(), login.getLogin(), login.getSenha(), login.getVencimento(), login.getCliente().getId(), login.getModifiedById(), login.getDataAlteracao() );
        return login;

    }

    public List<LoginDto> listarTodos(Long id) {
        return repository.findByVendedorLogado(id);
    }

    public List<LoginDto> listarTodosAtivos(Long id) {
        return repository.findAtivosByVendedorLogado(id);
    }
}
