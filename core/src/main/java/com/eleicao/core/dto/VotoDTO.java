package com.eleicao.core.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotoDTO {
    private String type;
    private String object;
    private Long valor;
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "VotoDTO{" +
                "type='" + type + '\'' +
                ", object='" + object + '\'' +
                ", valor=" + valor +
                ", timestamp=" + timestamp +
                '}';
    }
}
