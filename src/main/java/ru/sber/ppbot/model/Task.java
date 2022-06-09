package ru.sber.ppbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.EnumSet;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("task")
public class Task {

    @Id
    private Long id;

    private String name;

    //private List<Complexity> complexities;

    private EnumSet<Complexity> complexities;
}
