package com.trabalhosd.votacao.service;

import com.trabalhosd.votacao.dto.EleitorDTO;
import com.trabalhosd.votacao.entity.Candidato;
import com.trabalhosd.votacao.entity.Eleitor;
import com.trabalhosd.votacao.repository.CandidatoRepository;
import com.trabalhosd.votacao.repository.EleitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EleitorService {
    @Autowired
    private EleitorRepository eleitorRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    public Eleitor buscarPorId(String id) {
        Optional<Eleitor> eleitorOptional = eleitorRepository.findById(id);
        if (eleitorOptional.isPresent()) {
            return eleitorOptional.get();
        }
        return null;
    }

    public EleitorDTO criar(EleitorDTO eleitorDTO) {
        Eleitor eleitor = convertToEntity(eleitorDTO);
        Eleitor eleitorSalvo = eleitorRepository.save(eleitor);
        return convertToDTO(eleitorSalvo);
    }

    public Eleitor salvar(Eleitor eleitor){
        return eleitorRepository.save(eleitor);
    }

    public EleitorDTO convertToDTO(Eleitor eleitor) {
        EleitorDTO dto = new EleitorDTO();
        dto.setId(eleitor.getId());
        dto.setNome(eleitor.getNome());
        dto.setIdade(eleitor.getIdade());
        dto.setCidade(eleitor.getCidade());
        dto.setEstado(eleitor.getEstado());

        if (eleitor.getVoto() != null) {
            dto.setCandidatoId(eleitor.getVoto().getId());
        }

        return dto;
    }

    private Eleitor convertToEntity(EleitorDTO dto) {
        Eleitor eleitor = new Eleitor();
        eleitor.setId(dto.getId());
        eleitor.setNome(dto.getNome());
        eleitor.setIdade(dto.getIdade());
        eleitor.setCidade(dto.getCidade());
        eleitor.setEstado(dto.getEstado());

        if (dto.getCandidatoId() != null && !dto.getCandidatoId().isEmpty()) {
            Optional<Candidato> candidatoOptional = candidatoRepository.findById(dto.getCandidatoId());
            candidatoOptional.ifPresent(eleitor::setVoto);
        }

        return eleitor;
    }
}
