package com.eleicao.sd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class CandidatoDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String nome;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long quantidadeVotos;

    private String foto;

}
