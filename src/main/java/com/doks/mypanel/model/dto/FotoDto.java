package com.doks.mypanel.model.dto;

public class FotoDto {

    private Long id;
    private String foto;
    private String email;
    private Long idUsuario;

    public FotoDto() {
    }

    public FotoDto(Long id, String foto, String email, Long idUsuario) {
        this.id = id;
        this.foto = foto;
        this.email = email;
        this.idUsuario = idUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }






    public String getFoto() {
        return foto;
    }


    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
