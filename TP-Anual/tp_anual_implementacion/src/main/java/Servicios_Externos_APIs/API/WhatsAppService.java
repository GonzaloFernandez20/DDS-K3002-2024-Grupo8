//https://blog.ultramsg.com/es/como-enviar-whatsapp-api-usando-java/
public class WhatsAppService {

    private static final String BASE_URL = "https://api.ultramsg.com/instance19/messages/";
    private static final String API_TOKEN = "JATafg4422g0K54"; // Token de autenticación
    private static final OkHttpClient CLIENT = new OkHttpClient();

    public static void sendTextMessage(String to, String message) {
        // Crear el cuerpo de la solicitud con los parámetros necesarios
        String parameters = "token=" + API_TOKEN + "&to=" + to + "&body=" + message;
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, parameters);

        // Crear la solicitud HTTP
        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Mensaje enviado exitosamente: " + response.body().string());
            } else {
                System.err.println("Error al enviar mensaje: " + response.code() + " - " + response.body().string());
            }
        } catch (IOException e) {
            System.err.println("Excepción al enviar el mensaje:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendTextMessage("NUMERO", "MENSAJE");
    }
}
