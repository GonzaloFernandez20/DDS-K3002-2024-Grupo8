package Modelo.seguridad.SesionActiva;

import org.springframework.http.ResponseCookie;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GeneradorDeCookie {

    public static ResponseCookie generarCookie(String tokenBase){
        String tokenCodificado = URLEncoder.encode(tokenBase, StandardCharsets.UTF_8);

        ResponseCookie cookie = ResponseCookie.from("token", tokenCodificado)
                .httpOnly(true)
                //.secure(true)
                .path("/")
                .maxAge(60 * 60) // 1 hora
                .sameSite("Strict")
                .build();
        return cookie;
    }

// Devolvemos una cookie con la informacion del token, que luego el navegador se encargara de enviar ante cada solicitud

    public static ResponseCookie eliminarCookie(){
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                //.secure(true)
                .path("/")
                .maxAge(0) // 1 hora
                .sameSite("Strict")
                .build();
        return cookie;
    }
}

// Se elimina la cookie para cerrar sesion