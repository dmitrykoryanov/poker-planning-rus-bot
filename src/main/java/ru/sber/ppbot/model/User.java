package ru.sber.ppbot.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * User entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("user")
@Builder
@ToString
public class User {

    @Id
    private String username;

    private String chatId;
}

