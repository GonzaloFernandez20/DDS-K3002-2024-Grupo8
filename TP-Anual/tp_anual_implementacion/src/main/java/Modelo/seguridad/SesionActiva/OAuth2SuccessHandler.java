package Modelo.seguridad.SesionActiva;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.medios_de_contacto.Mail;
import Repositories.UsuariosRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UsuariosRepository userRepository;

    @Autowired
    public OAuth2SuccessHandler(UsuariosRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Obtener el usuario autenticado desde el contexto
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        String nombreDeUsuario = oauth2User.getAttribute("name");

        // Buscar si el usuario ya existe
        Optional<Usuario> usuario = userRepository.findByEmail(email);

        if (usuario.isEmpty()) {
            // Si no existe, creamos un nuevo usuario
            Colaborador colaborador = new Colaborador(null, new ArrayList<>());
            colaborador.agregarMedioDeContacto(new Mail(email));

            Usuario nuevoUsuario = new Usuario(nombreDeUsuario, null, colaborador);
            //nuevoUsuario.setColaborador(colaborador);
            //nuevoUsuario.setUsuario(nombreDeUsuario);

            // Guardar el nuevo usuario en la base de datos
            userRepository.save(nuevoUsuario);
            System.out.println("Usuario registrado con exito");

            // Crear un nuevo token con el nombre como principal
            Authentication nuevaAutenticacion = new UsernamePasswordAuthenticationToken(
                    nombreDeUsuario, // Aquí el atributo que necesitas como principal
                    oauth2User.getAttributes(),
                    oauth2User.getAuthorities()
            );

            // Actualizar el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(nuevaAutenticacion);

            response.sendRedirect("/ModificarColaborador");
        }
        // Redirigir a la página principal después del registro
        else response.sendRedirect("/Home");
    }
}
