package com.doks.mypanel.model;

import jakarta.persistence.*;


@Entity
@Table(name = "permissoes")
public class Permissoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;



    public enum PermissoesEnum {

        ADMINISTRADOR("Administrador"),
        REVENDEDOR("Revendedor");

        String descricao;

        PermissoesEnum(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    // Getters and Setters


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


    public enum Values {

        ADMINISTRADOR("Administrador"),
        REVENDEDOR("Revendedor");

        String descricao;

        Values(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }



}

