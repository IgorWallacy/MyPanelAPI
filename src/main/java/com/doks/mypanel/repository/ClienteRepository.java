
package com.doks.mypanel.repository;

import com.doks.mypanel.model.Cliente;
import com.doks.mypanel.model.dto.ClienteDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cliente (id, nome, email, whatsapp, foto, observacao, usuario_id) VALUES (:id, :nome, :email, :whatsapp, :foto, :observacao, :usuario_id) ", nativeQuery = true)
    void salvar(Long id, String nome,String email, String whatsapp, byte[] foto, String observacao, Long usuario_id);

    
    @Modifying
    @Transactional
    @Query(value = "UPDATE cliente SET " +
            "nome = COALESCE(NULLIF(:nome, ''), nome), " +
            "email = COALESCE(NULLIF(:email, ''), email), " +
            "whatsapp = COALESCE(NULLIF(:whatsapp, ''), whatsapp), " +
            "foto = COALESCE(:foto, foto), " +
            "observacao = COALESCE(NULLIF(:observacao, ''), observacao), " +
            "usuario_id = COALESCE(:usuario_id, usuario_id) " +
            "WHERE id = :id", nativeQuery = true)
    void atualizar(Long id, String nome, String email, String whatsapp, byte[] foto, String observacao, Long usuario_id);


    @Query("SELECT new com.doks.mypanel.model.dto.ClienteDto(c.id, c.nome, c.email, c.whatsapp, encode(c.foto , 'Base64' ) , CAST( c.observacao AS string), u.nome) " +
            "FROM Cliente c LEFT JOIN c.usuario u where c.usuario.id = :id ORDER BY c.nome")
    List<ClienteDto> todosClientesPorVendedor(Long id);


}
