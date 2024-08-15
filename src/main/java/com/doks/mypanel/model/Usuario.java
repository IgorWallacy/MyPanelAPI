package com.doks.mypanel.model;

import com.doks.mypanel.model.dto.UsuarioDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "usuario")
@EntityListeners(AuditingEntityListener.class)
public class Usuario extends UsuarioDto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "permissao_id", nullable = false)
    private Permissoes permissoes;

    @Column(name="vencimento" , nullable = false)
    private LocalDate vencimento;

    @Column(name="faturamento" , nullable = false)
    private Integer faturamento;

    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime dataCadastro;

    @Column(name = "data_alteracao")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private LocalDateTime dataAlteracao;

    @CreatedBy
    @Column(name = "criado_por_id")
    private Long createdById;

    @LastModifiedBy
    @Column(name = "modificado_por_id")
    private Long modifiedById;


    @ManyToOne
    @JoinColumn(name = "usuario_pai_id" , nullable = true)
    @JsonBackReference
    private Usuario usuarioPai;

    @OneToMany(mappedBy = "usuarioPai", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Usuario> subUsuarios;

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

    public Permissoes getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Permissoes permissoes) {
        this.permissoes = permissoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Usuario getUsuarioPai() {
        return usuarioPai;
    }

    public void setUsuarioPai(Usuario usuarioPai) {
        this.usuarioPai = usuarioPai;
    }

    public List<Usuario> getSubUsuarios() {
        return subUsuarios;
    }

    public void setSubUsuarios(List<Usuario> subUsuarios) {
        this.subUsuarios = subUsuarios;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public Integer getFaturamento() {
        return faturamento;
    }

    public void setFaturamento(Integer faturamento) {
        this.faturamento = faturamento;
    }
}
