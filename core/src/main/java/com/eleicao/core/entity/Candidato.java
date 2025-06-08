package com.eleicao.core.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;

    private Long quantidadeVotos;

    @Lob
    private byte[] foto;

    public Candidato(String nome, Long quantidadeVotos, byte[] bytes) {
        this.nome = nome;
        this.quantidadeVotos = quantidadeVotos;
        this.foto = bytes;
    }


    @PrePersist
    private void onCreateQuantidadeVotos(){
        this.quantidadeVotos = 0L;
    }


}
