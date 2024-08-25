package com.doks.mypanel.repository;

import com.doks.mypanel.model.Login;
import com.doks.mypanel.model.dto.LoginDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface LoginRepository extends JpaRepository<Login, Long> {

    @Query("SELECT new com.doks.mypanel.model.dto.LoginDto(" +
            "login.id, login.login, login.senha, login.vencimento, " +
            "login.cliente.id, login.createdById, login.dataAlteracao, " +
            "login.dataCadastro, login.modifiedById, usuario.id, " +
            "cliente.nome, usuario.nome ,encode(cliente.foto , 'Base64' ) ) as foto, CAST( cliente.observacao AS string)  " +
            " FROM Login login " +
            "LEFT JOIN login.cliente cliente " +
            "LEFT JOIN cliente.usuario usuario " +
            "WHERE usuario.id = ?1")
    List<LoginDto> findByVendedorLogado(Long id);

    @Query("SELECT new com.doks.mypanel.model.dto.LoginDto(" +
            "login.id, login.login, login.senha, login.vencimento, " +
            "login.cliente.id, login.createdById, login.dataAlteracao, " +
            "login.dataCadastro, login.modifiedById, usuario.id, " +
            "cliente.nome, usuario.nome ,encode(cliente.foto , 'Base64' ) ) as foto, CAST( cliente.observacao AS string)  " +
            " FROM Login login " +
            "LEFT JOIN login.cliente cliente " +
            "LEFT JOIN cliente.usuario usuario " +
            "WHERE usuario.id = ?1 and login.vencimento >= CURRENT_DATE")
    List<LoginDto> findAtivosByVendedorLogado(Long id);


    @Modifying
    @Transactional
    @Query(value = "UPDATE Login SET " +
            "login = COALESCE(NULLIF(?2, ''), login), " +
            "senha = COALESCE(NULLIF(?3, ''), senha), " +
            "vencimento = COALESCE(?4, vencimento), " +
            "cliente_id = COALESCE(?5, cliente_id), " +
            "modificado_por_id = COALESCE(?6, modificado_por_id), " +
            "data_alteracao = COALESCE(?7, data_alteracao) " +
            "WHERE id = ?1", nativeQuery = true)
    void atualizar(Long id, String login, String senha, LocalDate vencimento, Long idCliente, Long modifiedById, LocalDateTime dataAlteracao);
}
