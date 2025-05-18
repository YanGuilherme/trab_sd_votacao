package com.eleicao.sd.dto;

import java.time.LocalDateTime;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
