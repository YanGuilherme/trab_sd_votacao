package com.trabalhosd.votacao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Eleitor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;

    private BigDecimal idade;

    private String cidade;

    private String estado;

    @ManyToOne
    @JoinColumn(name = "candidato_id")
    private Candidato voto;
}
