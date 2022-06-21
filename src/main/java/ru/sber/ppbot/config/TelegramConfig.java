package ru.sber.ppbot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegram")
public class TelegramConfig {

    private String webhookPath;
    private String botName;
    private String botToken;
    private String apiUrl;
}
