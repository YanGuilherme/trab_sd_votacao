package com.eleicao.core.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidatoDTO {

    private Long id;

    private String nome;

    private Long quantidadeVotos;


    @Override
    public String toString() {
        return "CandidatoDTO{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
