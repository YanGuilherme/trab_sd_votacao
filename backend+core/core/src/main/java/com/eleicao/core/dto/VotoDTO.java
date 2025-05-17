package com.eleicao.core.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotoDTO {
    Long candidato_id;
    Long quantidade_votos;

    @Override
    public String toString() {
        return "VotoDTO{" +
                "candidato_id=" + candidato_id +
                ", quanditade_votos=" + quantidade_votos +
                '}';
    }
}
