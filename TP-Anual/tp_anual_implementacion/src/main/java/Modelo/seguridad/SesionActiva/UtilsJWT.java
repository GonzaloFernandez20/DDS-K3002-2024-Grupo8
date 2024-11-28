package Modelo.seguridad.SesionActiva;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Date;

public class UtilsJWT {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Establecemos la validez de nuestro token por un lapso de 1 hora (milisegundos)
    private static final long tiempoDeExpiracion = 3600000;

    public static String generarToken(String nombreDeUsuario){
        Date fechaActual = new Date();
        Date fechaDeExpiracion = new Date(fechaActual.getTime() + tiempoDeExpiracion);
        String token = Jwts.builder()
                .setSubject(nombreDeUsuario)
                .setIssuedAt(fechaActual)
                .setExpiration(fechaDeExpiracion)
                .signWith(key)
                .compact();
         return token;
    }

    public static boolean validarToken(String token){
        try {
            // Configuramos el parser con la clave secreta para validar la firma
            Jwts.parserBuilder()
                    .setSigningKey(key) // Establecemos la clave secreta
                    .build()
                    .parseClaimsJws(token);  // Parseamos y verificamos el token
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String obtenerSujetoDelToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


}
