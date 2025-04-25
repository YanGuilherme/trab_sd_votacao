package com.eleicao.sd.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;

    private Long quantidadeVotos;

    @PrePersist
    private void onCreateQuantidadeVotos(){
        this.quantidadeVotos = 0L;
    }

}
