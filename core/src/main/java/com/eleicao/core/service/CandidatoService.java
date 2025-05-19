package com.eleicao.core.service;

import com.eleicao.core.component.CandidatoListener;
import com.eleicao.core.dto.CandidatoDTO;
import com.eleicao.core.entity.Candidato;
import com.eleicao.core.repository.CandidatoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    private static final Logger logger = LogManager.getLogger(CandidatoService.class);


    public void criarCandidato(CandidatoDTO candidatoDTO){
        if (candidatoRepository.existsByNome(candidatoDTO.getNome())){
            logger.error("Candidato ja existe: {}", candidatoDTO.getNome());
            throw new RuntimeException("nome ja existe");
        }
        Candidato candidato = new Candidato();
        candidato.setNome(candidatoDTO.getNome());
        candidato.setFoto(candidatoDTO.getFoto());
        candidatoRepository.save(candidato);
    }

    public List<Candidato> listarCandidatos(){
        return candidatoRepository.findAll();
    }

    public List<Candidato> listarPorQuantidadeVotosDesc(){
        return candidatoRepository.findAllByOrderByQuantidadeVotosDesc();
    }
}
