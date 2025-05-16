package com.eleicao.core.repository;

import com.eleicao.core.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {}
