package com.doks.mypanel.auditing;

import com.doks.mypanel.model.Usuario;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            Long userId = jwt.getClaim("id");
            return Optional.ofNullable(userId);
        } else if (authentication.getPrincipal() instanceof Usuario) {
            Usuario userPrincipal = (Usuario) authentication.getPrincipal();
            return Optional.ofNullable(userPrincipal.getId());
        }

        return Optional.empty();
    }
}