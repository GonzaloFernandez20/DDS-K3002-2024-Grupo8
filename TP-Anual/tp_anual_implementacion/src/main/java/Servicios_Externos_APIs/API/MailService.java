package Servicios_Externos_APIs.API;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailService {

   public static String sendEmail(String subject, String message, String correo) {
        String host = "smtp.gmail.com";
        
        // Obtener las credenciales del correo desde variables de entorno
        final String user ="diseno.de.sistemas.24@gmail.com";
        final String password = "zeqi qrjx ivnw aovw";

        // Verificar que las credenciales no estén vacías
        if (user == null || user.isEmpty() || password == null || password.isEmpty()) {
            return "Error: Las credenciales no están configuradas correctamente en las variables de entorno.";
        }

        // Configurar propiedades del servidor SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Crear sesión autenticada
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            // Configurar el mensaje
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(user, "Admin Notificaciones"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));
            msg.setSubject(subject);
            msg.setText(message);

            // Enviar el correo
            Transport.send(msg);
            return "Correo enviado con éxito a " + correo;

        } catch (AuthenticationFailedException e) {
            System.err.println("Error de autenticación: " + e.getMessage());
            return "Error de autenticación al enviar el correo. Por favor, verifica las credenciales.";
        } catch (MessagingException e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
            return "Error al enviar el correo: " + e.getMessage();
        } catch (UnsupportedEncodingException e) {
            System.err.println("Error de codificación: " + e.getMessage());
            return "Error de codificación al enviar el correo.";
        }
    }
}
