package com.eleicao.core.service;

import com.eleicao.core.dto.VotoDTO;
import com.eleicao.core.entity.Candidato;
import com.eleicao.core.entity.Voto;
import com.eleicao.core.repository.CandidatoRepository;
import com.eleicao.core.repository.VotoRepository;
import jakarta.transaction.Transactional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;



    @Transactional
    public void salvarVoto(VotoDTO votoDTO){
        Voto voto = new Voto();
        voto.setType(votoDTO.getType());
        voto.setValor(votoDTO.getValor());
        voto.setObject(votoDTO.getObject());
        voto.setTimestamp(votoDTO.getTimestamp());
        votoRepository.save(voto);
    }

    @Transactional
    public void processarVoto(VotoDTO votoDTO) {
        Candidato candidato = candidatoRepository.findByNome(votoDTO.getObject());

        Long votos = candidato.getQuantidadeVotos();
        candidato.setQuantidadeVotos((votos == null ? 0 : votos) + 1);
        candidatoRepository.save(candidato);
    }
}
