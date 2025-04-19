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
public class EleitorDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    private String nome;
    private Integer idade;
    private String cidade;
    private String estado;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String candidatoId;
}
