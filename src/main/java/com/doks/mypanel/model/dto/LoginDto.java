package com.doks.mypanel.model.dto;

import org.apache.commons.codec.binary.Base64;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LoginDto {

    private Long id;
    private String login;
    private String senha;
    private LocalDate vencimento;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAlteracao;
    private Long createdById;
    private Long modifiedById;
    private Long clienteId;
    private Long usuarioId;
    private String nomeCliente;
    private String nomeUsuario;
    private String foto;


    public LoginDto(Long id, String login, String senha, LocalDate vencimento, Long clienteId, Long createdById, LocalDateTime dataAlteracao, LocalDateTime dataCadastro, Long modifiedById, Long usuarioId, String nomeCliente, String nomeUsuario, Object foto) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.vencimento = vencimento;
        this.clienteId = clienteId;
        this.createdById = createdById;
        this.dataAlteracao = dataAlteracao;
        this.dataCadastro = dataCadastro;
        this.modifiedById = modifiedById;
        this.usuarioId = usuarioId;
        this.nomeCliente = nomeCliente;
        this.nomeUsuario = nomeUsuario;
        if (foto == null || (foto instanceof String && ((String) foto).isEmpty())) {
            this.foto = "";
        } else if (foto instanceof byte[]) {
            this.foto = Base64.encodeBase64String((byte[]) foto);
        } else if (foto instanceof String) {
            this.foto = (String) foto;
        } else {
            throw new IllegalArgumentException("Tipo de foto inválido");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public Long getModifiedById() {
        return modifiedById;
    }

    public void setModifiedById(Long modifiedById) {
        this.modifiedById = modifiedById;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
