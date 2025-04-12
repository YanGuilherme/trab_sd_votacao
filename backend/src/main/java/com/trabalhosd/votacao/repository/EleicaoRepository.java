package com.trabalhosd.votacao.repository;

import com.trabalhosd.votacao.entity.Eleicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EleicaoRepository extends JpaRepository<Eleicao, String> {
}