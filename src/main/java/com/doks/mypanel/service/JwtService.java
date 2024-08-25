package com.doks.mypanel.service;

import com.doks.mypanel.model.Usuario;
import com.doks.mypanel.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Autowired
    private UsuarioRepository usuarioRepository;


    public String genertaeToken(Authentication authentication) {

        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(604800);

        String scopes = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Usuario  usuario = usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));






        var claims = JwtClaimsSet.builder()
                .issuer("MyPanel")
                .issuedAt(now)
                .expiresAt(expiry)
                .claim("scope", scopes)
                .claim("id", usuario.getId())
                .claim("nome", usuario.getNome())

                .subject(authentication.getName())


                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }
}
