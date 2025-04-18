package com.trabalhosd.votacao.service;

import com.trabalhosd.votacao.dto.CandidatoDTO;
import com.trabalhosd.votacao.entity.Candidato;
import com.trabalhosd.votacao.entity.Eleicao;
import com.trabalhosd.votacao.exception.CandidatoNotFoundException;
import com.trabalhosd.votacao.exception.EleicaoNotFoundException;
import com.trabalhosd.votacao.repository.CandidatoRepository;
import com.trabalhosd.votacao.repository.EleicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private EleicaoRepository eleicaoRepository;


    public CandidatoDTO findById(String id){
        Candidato candidato = candidatoRepository.findById(id)
                .orElseThrow(CandidatoNotFoundException::new);

        CandidatoDTO candidatoDTO = new CandidatoDTO();

        candidatoDTO.setId(candidato.getId());
        candidatoDTO.setNome(candidato.getNome());
        candidatoDTO.setLema(candidato.getLema());
        candidatoDTO.setEleicao_id(candidato.getEleicao().getId());

        return candidatoDTO;
    }

    public Candidato create(CandidatoDTO candidatoDTO){

        Eleicao eleicao = eleicaoRepository.findById(candidatoDTO.getEleicao_id())
                .orElseThrow(EleicaoNotFoundException::new);

        Candidato candidato = new Candidato();

        candidato.setNome(candidatoDTO.getNome());
        candidato.setLema(candidatoDTO.getLema());
        candidato.setEleicao(eleicao);

        return candidatoRepository.save(candidato);

    }
}