package com.doks.mypanel.model.dto;

import org.apache.commons.codec.binary.Base64;


public class ClienteDto {

    private Long id;

    private String nome;

    private String email;

    private String whatsapp;

    private String foto;

    private String observacao;

    private String usuarioNome;


    public ClienteDto(Long id, String nome, String email, String whatsapp, Object foto, String observacao, String usuarioNome) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.whatsapp = whatsapp;
        if (foto == null || (foto instanceof String && ((String) foto).isEmpty())) {
            this.foto = "";
        } else if (foto instanceof byte[]) {
            this.foto = Base64.encodeBase64String((byte[]) foto);
        } else if (foto instanceof String) {
            this.foto = (String) foto;
        } else {
            throw new IllegalArgumentException("Tipo de foto inválido");
        }
        this.observacao = observacao;
        this.usuarioNome = usuarioNome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }
}
