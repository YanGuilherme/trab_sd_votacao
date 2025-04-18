package com.trabalhosd.votacao.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Eleicao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String titulo;

    private String descricao;

    @JsonManagedReference
    @OneToMany(mappedBy = "eleicao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Candidato> candidatos = new ArrayList<>();

    private Double media_idade;




}
