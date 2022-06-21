package ru.sber.ppbot.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * <p>WebClient is required to send post requests to telegram (requests for user votes) </p>
 *
 *  @author Dmitry Koryanov
 */
@Slf4j
@Configuration
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WebClientConfiguration {

    private TelegramConfig telegramConfig;

    @Bean
    public WebClient webClient(){
        String telegramPOSTURI = telegramConfig.getApiUrl()+"bot"+telegramConfig.getBotToken()+"/";
        log.info("Создаём WebClient для отправки запросов по адресу {}",telegramPOSTURI);
        return WebClient.create(telegramPOSTURI);
    }

}
