package org.example.tradeitggskinchecker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramNotifierConfig {
    @Value("${Telegram.Token}")
    private String botToken;

    @Value("${Telegram.ChatId}")
    private String chatId;

    @Bean
    public TelegramNotifier telegramNotifier() {
        return new TelegramNotifier(botToken, chatId);
    }

}
