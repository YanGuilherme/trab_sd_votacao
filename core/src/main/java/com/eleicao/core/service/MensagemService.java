package com.eleicao.core.service;

import com.eleicao.core.dto.MensagemDTO;
import com.eleicao.core.entity.Candidato;
import com.eleicao.core.entity.Mensagem;
import com.eleicao.core.repository.CandidatoRepository;
import com.eleicao.core.repository.VotoRepository;
import jakarta.transaction.Transactional;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MensagemService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    private static final Logger logger = LogManager.getLogger(MensagemService.class);



    @Transactional //para grupo de votacao
    public void salvarVoto(MensagemDTO mensagemDTO, String nome_candidato){
        Mensagem mensagem = new Mensagem();
        mensagem.setType(mensagemDTO.getType());
        mensagem.setValor(mensagemDTO.getValor());
        mensagem.setObject(nome_candidato);
        mensagem.setTimestamp(mensagemDTO.getTimestamp());
        logger.info("Voto salvo: {}", mensagem.toString());
        votoRepository.save(mensagem);
    }

    @Transactional //para grupo de votacao
    public void processarVoto(MensagemDTO mensagemDTO) {
        try {
            Long id = Long.parseLong(mensagemDTO.getObject());

            Candidato candidato = candidatoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Candidato não encontrado."));

            String nome_candidato = candidato.getNome();
            Long votos = candidato.getQuantidadeVotos();
            candidato.setQuantidadeVotos((votos == null ? 0 : votos) + 1);

            salvarVoto(mensagemDTO, nome_candidato);
            candidatoRepository.save(candidato);

        } catch (NumberFormatException e) {
            throw new RuntimeException("ID do candidato inválido: deve ser um número.");
        }
    }

    @Transactional
    public void processarQualidadeAr(MensagemDTO mensagemDTO){

    }
}
