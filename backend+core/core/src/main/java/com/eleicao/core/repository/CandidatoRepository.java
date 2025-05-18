package com.eleicao.core.repository;

import com.eleicao.core.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    List<Candidato> findAllByOrderByQuantidadeVotosDesc();
    boolean existsByNome(String nome);
    Candidato findByNome(String nome);
}
