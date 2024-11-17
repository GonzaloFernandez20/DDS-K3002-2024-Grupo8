package Servicios_Externos_APIs.API;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;


public class WhatsAppService {

    private static final String BASE_URL = "https://api.ultramsg.com/instance19/messages/";
    private static final String API_TOKEN = "JATafg4422g0K54"; // Token de autenticación
    private static final OkHttpClient CLIENT = new OkHttpClient();

    public static void sendTextMessage(String numero, String message) {
        // Crear el cuerpo de la solicitud con los parámetros necesarios
        String parameters = "token=" + API_TOKEN + "&to=" + numero + "&body=" + message;
        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(parameters, mediaType);

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
}

