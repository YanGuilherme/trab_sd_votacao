package com.eleicao.sd.dto;

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
                ", quantidade_votos=" + quantidade_votos +
                '}';
    }
}
