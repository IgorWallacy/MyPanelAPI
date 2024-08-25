package com.doks.mypanel.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "login")
@EntityListeners(AuditingEntityListener.class)
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "login_seq", sequenceName = "login_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "login" , unique = true , nullable = false )
    private String login;

    @Column(name = "senha" , nullable = false)
    private String senha;

    @Column(name = "vencimento")
    private LocalDate vencimento;

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
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return Objects.equals(id, login.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
