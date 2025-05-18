package com.eleicao.core.dto;

import lombok.*;

@Getter
@Setter
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
