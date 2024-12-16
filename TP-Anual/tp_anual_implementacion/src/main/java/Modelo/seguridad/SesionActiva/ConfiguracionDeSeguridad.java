package Modelo.seguridad.SesionActiva;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfiguracionDeSeguridad {

    private final FiltroDeAutenticacionJWT filtroDeAutenticacionJWT;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .authorizeHttpRequests(authRequest -> authRequest
                        //.anyRequest().permitAll())
                        .requestMatchers("/", "/Home", "/InicioDeSesion", "/RegistroPersona", "/Mapa", "/Nosotros",
                                         "/Header", "/Footer", "/CierreDeSesion", "/ValidarUsuario",
                                         "/RegistrarColaboradorJuridico", "/RegistrarColaboradorHumano",
                                            "/heladerasEnElMapa", "/DetectarMovimiento", "/AutorizarApertura").permitAll()
                        .requestMatchers("/assets/**", "/img/**", "/reportes/**").permitAll()
                        .anyRequest().authenticated())
                //.formLogin(formularioLogin -> formularioLogin.loginPage("/InicioDeSesion").permitAll()) // Permitimos el acceso público a la página de login
                .exceptionHandling(excepcionNoLoggueado ->
                        excepcionNoLoggueado.authenticationEntryPoint((request, response, authException) -> {
                            String redirectUrl = request.getRequestURI();
                            if (request.getQueryString() != null) {
                                redirectUrl += "?" + request.getQueryString();
                            }
                            response.sendRedirect("/InicioDeSesion?redirect=" + redirectUrl); //Si el usuario no esta autenticado y trata de acceder a una ruta privada
                        })
                )
                .sessionManagement(sessionManager-> sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filtroDeAutenticacionJWT, UsernamePasswordAuthenticationFilter.class) // Primero deberia validar la validez del token en la cookie
                .build();
    }
}

/*

-> .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
No hay sesiones guardadas en el servidor.
El cliente debe enviar la información de autenticación (un token JWT) con cada solicitud.
El servidor no guarda el estado entre las solicitudes.

*/
