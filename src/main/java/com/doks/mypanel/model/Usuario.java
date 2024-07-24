package com.doks.mypanel.model;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private String senha;

    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_permissoes",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "permissoes_id")
    )
    private Set<Permissoes> permissoes;

    // Getters and Setters


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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Permissoes> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Set<Permissoes> permissoes) {
        this.permissoes = permissoes;
    }
}
