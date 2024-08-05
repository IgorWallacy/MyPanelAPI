package com.doks.mypanel.controller;

import com.doks.mypanel.model.Foto;
import com.doks.mypanel.model.Usuario;
import com.doks.mypanel.model.dto.FotoDto;
import com.doks.mypanel.repository.FotoRepository;

import com.doks.mypanel.repository.UsuarioRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/usuario/perfil/foto")
public class UsuarioFotoController {

    @Autowired
    FotoRepository fotoRepository;


    @Autowired
    UsuarioRepository usuarioRepository;

    private FotoDto mapToFotoDto(Object[] tuple) {
        FotoDto fotoDto = new FotoDto();
        fotoDto.setId(((Long) tuple[0]));
        fotoDto.setFoto((String) tuple[1]);
        fotoDto.setIdUsuario(((Long) tuple[2]));
        return fotoDto;
    }



    @PostMapping
    public ResponseEntity<FotoDto> atualizarFoto(@RequestBody FotoDto fotoDto) {
        try {
            // Decodificar string Base64 para byte[]
            byte[] fotoBytes = Base64.decodeBase64(fotoDto.getFoto());




            // Criar entidade Foto e definir os valores
            Foto foto = new Foto();

            foto.setId(fotoDto.getId());

            usuarioRepository.findByEmail(fotoDto.getEmail()).ifPresent(usuario -> {
                foto.setIdUsuario(usuario.getId());
            }
            );

            foto.setFoto(fotoBytes);


            // Salvar a entidade Foto no repositório
            fotoRepository.saveOrUpdateFoto(foto.getId(), foto.getFoto(), foto.getIdUsuario());



            return ResponseEntity.ok(fotoDto);

        } catch (IllegalArgumentException e) {
            // Exceção lançada se a string Base64 não for válida
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }


    @GetMapping("/{email}")
    public ResponseEntity<byte[]> buscarFoto(@PathVariable("email") String email) {

            Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

            if (usuario.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Optional<List<Object[]>> fotoResult = fotoRepository.findByIdUsuario(usuario.get().getId());
            if (fotoResult.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            FotoDto fotoDto = mapToFotoDto(fotoResult.get().get(0));



        return ResponseEntity.ok().header("Content-Type", "image/jpeg")
                .body(Base64.decodeBase64(fotoDto.getFoto()));


    }

    @GetMapping("/fotoID/{email}")
    public ResponseEntity<FotoDto> buscarFotoID(@PathVariable("email") String email) {

        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<List<Object[]>> fotoResult = fotoRepository.findByIdUsuario(usuario.get().getId());
        if (fotoResult.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        FotoDto fotoDto = mapToFotoDto(fotoResult.get().get(0));



        return ResponseEntity.ok(fotoDto);


    }

    }

