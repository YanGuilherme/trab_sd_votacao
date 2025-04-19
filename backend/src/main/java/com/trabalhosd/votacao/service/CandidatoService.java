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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private EleicaoRepository eleicaoRepository;


    public CandidatoDTO findById(String id) {
        Candidato candidato = candidatoRepository.findById(id)
                .orElseThrow(CandidatoNotFoundException::new);

        return convertToDTO(candidato);
    }

    public CandidatoDTO create(CandidatoDTO candidatoDTO) {

        Eleicao eleicao = eleicaoRepository.findById(candidatoDTO.getEleicao_id())
                .orElseThrow(EleicaoNotFoundException::new);

        Candidato candidato = new Candidato();

        candidato.setNome(candidatoDTO.getNome());
        candidato.setLema(candidatoDTO.getLema());
        candidato.setEleicao(eleicao);
        candidato.setPartido(candidatoDTO.getPartido());

        candidatoRepository.save(candidato);
        return convertToDTO(candidato);


    }

    public List<Candidato> listarCandidatos(){ return candidatoRepository.findAll();}

    public List<CandidatoDTO> listarCandidatosPorEleicao(String eleicaoId) {
        List<Candidato> candidatos = candidatoRepository.findByEleicaoId(eleicaoId);
        return candidatos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private CandidatoDTO convertToDTO(Candidato candidato) {
        CandidatoDTO dto = new CandidatoDTO();
        dto.setId(candidato.getId());
        dto.setNome(candidato.getNome());
        dto.setLema(candidato.getLema());
        dto.setEleicao_id(candidato.getEleicao().getId());
        dto.setPartido(candidato.getPartido());
        dto.setQuantidade_votos(candidato.getQuantidade_votos());
        return dto;
    }


    public void deleteCandidato(String id_delete){
        candidatoRepository.deleteById(id_delete);
    }
}