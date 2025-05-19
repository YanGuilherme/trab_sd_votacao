package com.eleicao.sd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class CandidatoDTO {


    private String nome;

    private String foto;

    @Override
    public String toString() {
        return "CandidatoDTO{" +
                "nome='" + nome + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}