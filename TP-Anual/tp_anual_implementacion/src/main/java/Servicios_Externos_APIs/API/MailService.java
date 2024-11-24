package Servicios_Externos_APIs.API;

/*import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;*/

public class MailService {
//    public static void sendEmail(String toEmail, String subject, String plainText) {
//        String host = "smtp.gmail.com"; // Cambiar si se usa otro servicio
//        //falta crear el usuario que envia el correo
//        final String user = "diseno.de.sistemas.24@gmail.com";
//        final String password = "DISENO2024";
//
//        Properties props = new Properties();
//        props.put("mail.smtp.host", host);
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//
//        Session session = Session.getInstance(props, new Authenticator() {// Crear sesión autenticada
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(user, password);
//            }
//        });
//
//        try {
//            // Configurar el mensaje
//            Message msg = new MimeMessage(session);
//            msg.setFrom(new InternetAddress(user, "Admin Notificaciones"));
//            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
//            msg.setSubject(subject);
//
//            Multipart multipart = new MimeMultipart();// Crear contenido del correo
//
//            if (plainText != null && !plainText.isEmpty()) {// Parte de texto plano
//                MimeBodyPart textPart = new MimeBodyPart();
//                textPart.setText(plainText, "UTF-8");
//                multipart.addBodyPart(textPart);
//            }
//
//            msg.setContent(multipart);// Establecer el contenido del mensaje
//
//            // Enviar el correo
//            Transport.send(msg);
//            System.out.println("Correo enviado con éxito a " + toEmail);
//
//        } catch (MessagingException | IOException e) {
//            e.printStackTrace();
//            System.err.println("Error al enviar correo: " + e.getMessage());
//        }
//    }
}
