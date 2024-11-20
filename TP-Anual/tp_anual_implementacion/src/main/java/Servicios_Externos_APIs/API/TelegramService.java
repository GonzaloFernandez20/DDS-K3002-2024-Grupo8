package Servicios_Externos_APIs.API;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TelegramService {
    private static final String TELEGRAM_API_BASE = "https://api.telegram.org/bot";
    private static final String TOKEN = "7676554277:AAFhDgNgPHT7vQxdYlUY04kf7G_Z6MGQoF8";
    private static final OkHttpClient CLIENT = new OkHttpClient();

    // Para almacenar chat ID -- Esto tiene que ir a la BDD
    private static final Set<String> chatIds = new HashSet<>();

    public static void sendTextMessage(String chatId, String message) {
        String url = TELEGRAM_API_BASE + TOKEN + "/sendMessage";
        String jsonBody = String.format("{\"chat_id\": \"%s\", \"text\": \"%s\", \"parse_mode\": \"HTML\"}", chatId, message);

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(jsonBody, MediaType.get("application/json")))
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Mensaje enviado exitosamente.");
            } else {
                System.err.println("Error al enviar: " + response.code() + " - " + response.body().string());
            }
        } catch (IOException e) {
            System.err.println("Error de conexión:");
            e.printStackTrace();
        }
    }

    public static void getUpdates() {
        String url = TELEGRAM_API_BASE + TOKEN + "/getUpdates";

        Request request = new Request.Builder().url(url).build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONArray resultArray = jsonResponse.getJSONArray("result");

                for (int i = 0; i < resultArray.length(); i++) {
                    JSONObject update = resultArray.getJSONObject(i);
                    if (update.has("message")) {
                        JSONObject message = update.getJSONObject("message");
                        String chatId = message.getJSONObject("chat").getString("id");

                        // Detecta el comando /start
                        if (message.has("text") && message.getString("text").equalsIgnoreCase("/start")) {
                            // Guarda el chat_id
                            if (chatIds.add(chatId)) {
                                sendTextMessage(chatId, "¡Bienvenido! Tu chat ID ha sido registrado.");
                                System.out.println("Chat ID registrado: " + chatId);
                            } else {
                                sendTextMessage(chatId, "¡Ya estás registrado!");
                            }
                        }
                    }
                }
            } else {
                System.err.println("Error al obtener actualizaciones: " + response.code());
            }
        } catch (IOException e) {
            System.err.println("Error de conexión:");
            e.printStackTrace();
        }
    }
}
