package org.example.tradeitggskinchecker;

import lombok.AllArgsConstructor;


import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


@AllArgsConstructor
public class TelegramNotifier {
    private String botToken;
    private String chatId;

    public void sendMessage(String message) {
        try {
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
            String url = String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",
                    botToken, chatId, encodedMessage);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Telegram response: " + response.body());
            if (response.statusCode() != 200) {
                System.err.println("Error: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
