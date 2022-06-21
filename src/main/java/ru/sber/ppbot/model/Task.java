package ru.sber.ppbot.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.*;

/**
 * Task entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("task")
@Builder
@ToString
public class Task {

    @Id
    private Long id;

    private String name;

    private Map<String, Complexity> complexities = new HashMap<>();

    public Map<String, Complexity> getComplexities() {

        if(complexities == null){
            return new HashMap<>();
        }
        return complexities;
    }


    public double getComplexity(){

        if (complexities == null) {
            return 0.0;
        }

        return this.complexities
                .values()
                .stream()
                .map(Complexity::getComplexity)
                .mapToDouble(i -> (double) i)
                .average()
                .orElse(0.0);
    }
}
