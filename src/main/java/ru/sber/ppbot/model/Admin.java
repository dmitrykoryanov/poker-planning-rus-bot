package ru.sber.ppbot.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Admin entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("admin")
@Builder
@ToString
public class Admin {

    @Id
    private String name;
}
