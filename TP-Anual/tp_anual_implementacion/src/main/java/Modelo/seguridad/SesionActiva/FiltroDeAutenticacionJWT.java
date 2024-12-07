package Modelo.seguridad.SesionActiva;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.annotations.Comment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component
public class FiltroDeAutenticacionJWT extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Leemos el token que contiene la cookie
        Cookie[] cookies = request.getCookies();
        String token = null;

        if (cookies != null){
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())){
                    token = cookie.getValue();
                }
            }
        }

        if (token != null) {
            try {
                String tokenDecodificado = URLDecoder.decode(token, StandardCharsets.UTF_8);
                if (UtilsJWT.validarToken(tokenDecodificado)) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            UtilsJWT.obtenerSujetoDelToken(tokenDecodificado), null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (IllegalArgumentException e) {
                logger.error("Error al decodificar el token: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
