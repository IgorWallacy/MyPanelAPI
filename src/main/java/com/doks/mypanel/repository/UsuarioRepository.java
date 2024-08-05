package com.doks.mypanel.repository;

import com.doks.mypanel.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

     Optional<Usuario> findByEmail(String email);

     @Query(value = " select foto.foto  from foto left join usuario on (usuario.id = foto.id_usuario) where usuario.email =?1" , nativeQuery = true)
     String getFoto(String email);
}
