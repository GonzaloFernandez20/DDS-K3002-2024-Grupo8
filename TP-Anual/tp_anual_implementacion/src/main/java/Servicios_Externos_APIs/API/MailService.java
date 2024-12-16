package Servicios_Externos_APIs.API;

import javax.mail.*;
import javax.mail.internet.*;

import Modelo.Dominio.colaborador.Colaborador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailService {

   public static void sendEmail(String subject, String message, String correo) {
        String host = "smtp.gmail.com";
        
        // Obtener las credenciales del correo desde variables de entorno
        final String user ="diseno.de.sistemas.24@gmail.com";
        final String password = "zeqi qrjx ivnw aovw";

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

        } catch (AuthenticationFailedException e) {
            logger.error("Error de autenticación: {}", e.getMessage());
        } catch (MessagingException e) {
            logger.error("Error al enviar el correo: {}", e.getMessage());
        } catch (UnsupportedEncodingException e) {
            logger.error("Error de codificación: {}", e.getMessage());
        }
    }
    //Logger ----------------------------------------------------------------------------------------------------------
    private static final Logger logger = LoggerFactory.getLogger(Colaborador.class);
}
