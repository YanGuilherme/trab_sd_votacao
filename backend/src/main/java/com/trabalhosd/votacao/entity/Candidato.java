package com.trabalhosd.votacao.entity;

import jakarta.persistence.*;

@Entity
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "eleicao_id", nullable = false) // Cria a chave estrangeira no banco
    private Eleicao eleicao;

}
