package com.doks.mypanel.service;


import com.doks.mypanel.model.Usuario;
import com.doks.mypanel.model.dto.UsuarioDto;
import com.doks.mypanel.repository.FotoRepository;
import com.doks.mypanel.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    FotoRepository fotoRepository;


    public List<UsuarioDto> buscarTodos() {
        List<UsuarioDto> lista = new ArrayList<>();
        List<Object[]> results = repository.buscarUsuarioDto();

        for (Object[] result : results) {
            UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setFoto((String) result[0]);
            usuarioDto.setId(((Number) result[1]).longValue());
            usuarioDto.setNome((String) result[2]);
            usuarioDto.setEmail((String) result[3]);
            usuarioDto.setPermissao((String) result[4]);
            lista.add(usuarioDto);
        }

        return lista;
    }

    public List<Usuario> getFilhos(Long usuarioId) {
        Usuario usuario = repository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return repository.findByUsuarioPaiId(usuario.getId()).stream()
                .filter(filho -> !filho.getId().equals(usuarioId))
                .collect(Collectors.toList());
    }


    public List<UsuarioDto> getUsuarioPaiEDescendentes(Long usuarioId) {
        UsuarioDto usuarioPai = repository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

             setFotoForUsuario(usuarioPai);


        // Recuperar a foto do usuário em base64
        Optional<List<Object[]>> fotoBytes = fotoRepository.findByIdUsuario(usuarioId);

        if (fotoBytes.isPresent() && !fotoBytes.get().isEmpty()) {
            byte[] fotoBase64 = Base64.decodeBase64(fotoBytes.get().get(0)[1].toString().getBytes());
            usuarioPai.setFoto(Base64.encodeBase64String(fotoBase64));


        } else {
            // Handle the case where fotoBytes is empty or not present
            usuarioPai.setFoto(null); // or some default value
        }



        // Obter todos os descendentes
        List<UsuarioDto> todosDescendentes = getDescendentesRecursivamente(usuarioPai);

        // Set photo for each descendant
        todosDescendentes.forEach(this::setFotoForUsuario);

        // Remover os descendentes da lista principal
        List<UsuarioDto> listaPrincipal = todosDescendentes.stream()
                .filter(descendente -> !descendente.getId().equals(usuarioId))
                .collect(Collectors.toList());



        // Retornar a lista com o usuário pai no início
        List<UsuarioDto> resultado = new ArrayList<>();
        resultado.add(usuarioPai);
        return resultado;
    }

    private List<UsuarioDto> getDescendentesRecursivamente(UsuarioDto usuario) {
        List<UsuarioDto> descendentes = repository.findByUsuarioPai(usuario);
        List<UsuarioDto> todosDescendentes = new ArrayList<>(descendentes);

        for (UsuarioDto filho : descendentes) {
            todosDescendentes.addAll(getDescendentesRecursivamente(filho));


        }

        return todosDescendentes;
    }


    private void setFotoForUsuario(UsuarioDto usuario) {
        Optional<List<Object[]>> fotoBytes = fotoRepository.findByIdUsuario(usuario.getId());
        if (fotoBytes.isPresent() && !fotoBytes.get().isEmpty()) {
            byte[] fotoBase64 = Base64.decodeBase64(fotoBytes.get().get(0)[1].toString().getBytes());
            usuario.setFoto(Base64.encodeBase64String(fotoBase64));
        }
    }

}
