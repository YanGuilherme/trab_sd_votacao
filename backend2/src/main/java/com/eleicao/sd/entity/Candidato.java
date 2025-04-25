package com.eleicao.sd.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String nome;

    private Long quantidadeVotos;

    public Candidato() {
        this.quantidadeVotos = 0L;
    }
}
