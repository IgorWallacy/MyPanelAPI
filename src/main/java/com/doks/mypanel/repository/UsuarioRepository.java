package com.doks.mypanel.repository;

import com.doks.mypanel.model.Usuario;
import com.doks.mypanel.model.dto.UsuarioDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

     Optional<Usuario> findByEmail(String email);

     @Query(value = " select foto.foto  from foto left join usuario on (usuario.id = foto.id_usuario) where usuario.email =?1" , nativeQuery = true)
     String getFoto(String email);

     @Query(value = "SELECT\n" +
             "\tencode( foto.foto, 'base64' ) AS foto,\n" +
             "\tusuario.ID,\n" +
             "\tusuario.nome,\n" +
             "\tusuario.email,\n" +
             "\tpermissoes.nome AS permissao \n" +
             "FROM\n" +
             "\tusuario\n" +
             "\tLEFT JOIN permissoes ON ( permissoes.id = usuario.permissao_id )\n" +
             "\tLEFT JOIN foto ON (\n" +
             "\tfoto.id_usuario = usuario.ID) order by usuario.id desc" , nativeQuery = true)
     List<Object[]> buscarUsuarioDto();


     List<Usuario> findByUsuarioPaiId(Long usuarioPaiId);


     List<UsuarioDto> findByUsuarioPai(UsuarioDto usuario);
}
