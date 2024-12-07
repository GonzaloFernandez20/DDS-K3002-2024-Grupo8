package Servicios_Externos_APIs.API;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailService {

    public static void sendEmail(String subject, String message, String correo) {
        String host = "smtp.gmail.com"; 

        // correo del remitente
        final String user = "diseno.de.sistemas.24@gmail.com";
        final String password = "DISENO2024";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(user, "Admin Notificaciones")); // Remitente
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(correo)); // Destinatario
            msg.setSubject(subject);

            // Establecer el contenido del mensaje
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(message, "UTF-8"); 

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart); 

            msg.setContent(multipart);

            Transport.send(msg);
            System.out.println("Correo enviado con éxito a " + correo);

        } catch (MessagingException | UnsupportedEncodingException e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}