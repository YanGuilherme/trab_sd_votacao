package com.eleicao.core.service;

import com.eleicao.core.dto.VotoDTO;
import com.eleicao.core.entity.Candidato;
import com.eleicao.core.entity.Voto;
import com.eleicao.core.repository.CandidatoRepository;
import com.eleicao.core.repository.VotoRepository;
import jakarta.transaction.Transactional;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    private static final Logger logger = LogManager.getLogger(VotoService.class);

    @Transactional
    public void salvarVoto(VotoDTO votoDTO, String nome_candidato){
        Voto voto = new Voto();
        voto.setType(votoDTO.getType());
        voto.setValor(votoDTO.getValor());
        voto.setObject(nome_candidato);
        voto.setTimestamp(votoDTO.getTimestamp());
        logger.info("Voto salvo: {}", voto.toString());
        votoRepository.save(voto);
    }

    @Transactional
    public void processarVoto(VotoDTO votoDTO) {
        try {
            Long id = Long.parseLong(votoDTO.getObject());

            Candidato candidato = candidatoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Candidato não encontrado."));

            String nome_candidato = candidato.getNome();
            Long votos = candidato.getQuantidadeVotos();
            candidato.setQuantidadeVotos((votos == null ? 0 : votos) + 1);

            salvarVoto(votoDTO, nome_candidato);
            candidatoRepository.save(candidato);

        } catch (NumberFormatException e) {
            throw new RuntimeException("ID do candidato inválido: deve ser um número.");
        }
    }
}
