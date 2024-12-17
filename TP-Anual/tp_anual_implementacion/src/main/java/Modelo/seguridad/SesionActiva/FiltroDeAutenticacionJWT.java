package Modelo.seguridad.SesionActiva;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        String sessionId = null;

        if (cookies != null){
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())){
                    token = cookie.getValue();
                }else if ("JSESSIONID".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                }
            }
        }

        // Si existe la cookie JSESSIONID, confiamos en la autenticación del contexto de seguridad
        if (sessionId != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                //logger.info("Usuario autenticado con JSESSIONID: " + authentication.getName());
            } else {
                //logger.warn("JSESSIONID presente pero usuario no autenticado en el contexto.");
            }
        }

        if (token != null) {
            try {
                String tokenDecodificado = URLDecoder.decode(token, StandardCharsets.UTF_8);
                if (UtilsJWT.validarToken(tokenDecodificado)) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            UtilsJWT.obtenerSujetoDelToken(tokenDecodificado), null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    //logger.info("Usuario autenticado con JWT: " + authentication.getName());
                }
            } catch (IllegalArgumentException e) {
                //logger.error("Error al decodificar el token: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}