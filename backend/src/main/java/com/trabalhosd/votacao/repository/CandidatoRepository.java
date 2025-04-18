package com.trabalhosd.votacao.repository;

import com.trabalhosd.votacao.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, String> {
}