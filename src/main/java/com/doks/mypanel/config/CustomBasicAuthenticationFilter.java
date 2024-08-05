package com.doks.mypanel.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CustomBasicAuthenticationFilter extends BasicAuthenticationFilter {

    private static final String BASIC_AUTH_HEADER_PREFIX = "Basic ";
    private static final String PREDEFINED_USERNAME = "doks";
    private static final String PREDEFINED_PASSWORD = "doks123";

    public CustomBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith(BASIC_AUTH_HEADER_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String[] tokens = extractAndDecodeHeader(header);
            assert tokens.length == 2;
            String username = tokens[0];
            String password = tokens[1];

            if (PREDEFINED_USERNAME.equals(username) && PREDEFINED_PASSWORD.equals(password)) {
                Authentication auth = new UsernamePasswordAuthenticationToken(username, password, null);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            // Handle error
        }

        chain.doFilter(request, response);
    }

    private String[] extractAndDecodeHeader(String header) throws IOException {
        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new IOException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");
        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new IOException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }
}