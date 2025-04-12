package com.trabalhosd.votacao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;

    private String lema;

    @ManyToOne
    @JoinColumn(name = "eleicao_id", nullable = false) // Cria a chave estrangeira no banco
    private Eleicao eleicao;

}
