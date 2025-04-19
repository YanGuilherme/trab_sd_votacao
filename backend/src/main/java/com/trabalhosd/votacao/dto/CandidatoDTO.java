package com.trabalhosd.votacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidatoDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    private String nome;

    private String lema;

    private String eleicao_id;

    private String partido;

    private Integer quantidade_votos;
}