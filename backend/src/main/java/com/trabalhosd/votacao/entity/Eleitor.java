package com.trabalhosd.votacao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

    private Integer idade;

    private String cidade;

    private String estado;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "eleitor_eleicao",
            joinColumns = @JoinColumn(name = "eleitor_id"),
            inverseJoinColumns = @JoinColumn(name = "eleicao_id")
    )
    private Set<Eleicao> eleicoesParticipadas = new HashSet<>();

    public boolean jaVotouNaEleicao(String eleicaoId) {
        return eleicoesParticipadas.stream()
                .anyMatch(eleicao -> eleicao.getId().equals(eleicaoId));
    }

    public void adicionarEleicaoParticipada(Eleicao eleicao) {
        this.eleicoesParticipadas.add(eleicao);
    }

}
