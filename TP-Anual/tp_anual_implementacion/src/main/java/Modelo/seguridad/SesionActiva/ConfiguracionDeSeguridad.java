package Modelo.seguridad.SesionActiva;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfiguracionDeSeguridad {

    private final FiltroDeAutenticacionJWT filtroDeAutenticacionJWT;
    private final OAuth2SuccessHandler oauth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/", "/Home", "/InicioDeSesion", "/RegistroPersona", "/Mapa", "/Nosotros",
                                         "/Header", "/Footer", "/CierreDeSesion", "/ValidarUsuario",
                                         "/RegistrarColaboradorJuridico", "/RegistrarColaboradorHumano",
                                            "/heladerasEnElMapa").permitAll()
                        .requestMatchers("/assets/**", "/img/**", "/reportes/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(excepcionNoLoggueado ->
                        excepcionNoLoggueado.authenticationEntryPoint((request, response, authException) -> {
                            String redirectUrl = request.getRequestURI();
                            if (request.getQueryString() != null) {
                                redirectUrl += "?" + request.getQueryString();
                            }
                            response.sendRedirect("/InicioDeSesion?redirect=" + redirectUrl);
                        })
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/InicioDeSesion")
                        .successHandler(oauth2SuccessHandler)
                )
                .addFilterBefore(filtroDeAutenticacionJWT, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}