// https://cloud.google.com/appengine/docs/standard/services/mail/sending-mail-with-mail-api?hl=es-419&tab=java#top
class EmailService {
    public static void sendEmail(String toEmail, String subject, String plainText, String htmlText, List<File> attachments) {
        String host = "smtp.gmail.com"; // Cambiar si se usa otro servicio
        final String user = AppConfig.get("XXXX"); // Usuario remitente
        final String password = AppConfig.get("XXXX"); // Contraseña remitente

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {// Crear sesión autenticada
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            // Configurar el mensaje
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(user, "Admin Notificaciones"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject(subject);
            
            Multipart multipart = new MimeMultipart();// Crear contenido del correo

            if (plainText != null && !plainText.isEmpty()) {// Parte de texto plano
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText(plainText, "UTF-8");
                multipart.addBodyPart(textPart);
            }

            if (htmlText != null && !htmlText.isEmpty()) { // Parte de contenido HTML
                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(htmlText, "text/html");
                multipart.addBodyPart(htmlPart);
            }

            if (attachments != null) {// Adjuntar archivos (si existen)
                for (File file : attachments) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    attachmentPart.attachFile(file);
                    multipart.addBodyPart(attachmentPart);
                }
            }

            msg.setContent(multipart);// Establecer el contenido del mensaje

            // Enviar el correo
            Transport.send(msg);
            System.out.println("Correo enviado con éxito a " + toEmail);

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            System.err.println("Error al enviar correo: " + e.getMessage());
        }
    }
}
