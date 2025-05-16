package com.eleicao.sd.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotoDTO {
    Long candidato_id;
    Long quantidade_votos;
}
