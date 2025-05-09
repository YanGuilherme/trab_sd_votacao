package com.trabalhosd.votacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EleicaoDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    private String titulo;

    private String descricao;



}
