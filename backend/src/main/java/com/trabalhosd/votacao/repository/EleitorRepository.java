package com.trabalhosd.votacao.repository;

import com.trabalhosd.votacao.entity.Eleitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EleitorRepository extends JpaRepository<Eleitor, String> {
}
