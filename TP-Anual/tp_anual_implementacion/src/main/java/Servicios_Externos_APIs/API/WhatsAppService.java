package Servicios_Externos_APIs.API;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WhatsAppService {

    private static final String BASE_URL = "https://api.ultramsg.com/instance101715/messages/chat";
    private static final String API_TOKEN = "sfg839fvjc0a2ov3"; 
    private static final OkHttpClient CLIENT = new OkHttpClient();

    public static void sendTextMessage(String numero, String mensaje) {
        RequestBody body = new FormBody.Builder()
                .add("token", API_TOKEN)
                .add("to", "+549"+ numero) // numero del receptor
                .add("body", mensaje) 
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            assert response.body() != null;
            if (response.isSuccessful()) {
                logger.debug("Mensaje enviado exitosamente: {}", response.body().string());
            } else {
                logger.error("Error al enviar mensaje: {} - {}", response.code(), response.body().string());
            }
        } catch (IOException e) {
            logger.error("Excepción al enviar el mensaje:");
            e.printStackTrace();
        }
    }
    //Logger ----------------------------------------------------------------------------------------------------------
    private static final Logger logger = LoggerFactory.getLogger(WhatsAppService.class);
}
