package com.doks.mypanel.repository;

import com.doks.mypanel.model.Foto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO foto (id, foto, id_usuario) VALUES (:id, :foto, :idUsuario) ON CONFLICT (id) DO UPDATE SET foto = :foto, id_usuario = :idUsuario", nativeQuery = true)
    void saveOrUpdateFoto(Long id, byte[] foto, Long idUsuario);

    @Query(value = "select id, encode(foto , 'base64'), id_usuario from foto where id_usuario=?1", nativeQuery = true)
    Optional<List<Object[]>> findByIdUsuario(Long id);
}
