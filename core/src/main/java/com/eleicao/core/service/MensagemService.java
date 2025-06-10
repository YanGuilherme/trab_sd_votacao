package com.eleicao.core.service;

import com.eleicao.core.dto.MensagemDTO;
import com.eleicao.core.entity.Candidato;
import com.eleicao.core.entity.Cidade;
import com.eleicao.core.entity.Mensagem;
import com.eleicao.core.repository.CandidatoRepository;
import com.eleicao.core.repository.CidadeRepository;
import com.eleicao.core.repository.MensagemRepository;
import jakarta.transaction.Transactional;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    private static final Logger logger = LogManager.getLogger(MensagemService.class);



    @Transactional //para grupo de votacao
    public void salvarVoto(MensagemDTO mensagemDTO, String nome_candidato){
        Mensagem mensagem = new Mensagem();
        mensagem.setType(mensagemDTO.getType());
        mensagem.setValor(mensagemDTO.getValor());
        mensagem.setObject(nome_candidato);
        mensagem.setDateTime(mensagemDTO.getDateTime());
        logger.info("Voto salvo: {}", mensagem.toString());
        mensagemRepository.save(mensagem);
    }

    @Transactional
    public void salvarAtualizacaoAr(MensagemDTO mensagemDTO){
        Mensagem mensagem = new Mensagem();
        mensagem.setType(mensagemDTO.getType());
        mensagem.setObject(mensagemDTO.getObject());
        mensagem.setValor(mensagemDTO.getValor());
        mensagem.setDateTime(mensagemDTO.getDateTime());
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
        try{
            Long id = Long.parseLong(mensagemDTO.getObject());

            Cidade cidade = cidadeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cidade não encontrada"));

            cidade.setValor_qualidade_ar(mensagemDTO.getValor());
            salvarAtualizacaoAr(mensagemDTO);

            // chamar as funcoes para calcular media mediana.


            cidadeRepository.save(cidade);

        }catch (NumberFormatException e){
            throw new RuntimeException("ID da cidade inválido: deve ser um número.");
        }

    }


}
