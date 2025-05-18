package com.eleicao.core.component;

import com.eleicao.core.configuration.RabbitConfig;
import com.eleicao.core.dto.CandidatoDTO;
import com.eleicao.core.dto.VotoDTO;
import com.eleicao.core.service.CandidatoService;
import com.eleicao.core.service.VotoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class CandidatoListener {
    private static final Logger logger = LogManager.getLogger(CandidatoListener.class);

    @Autowired
    private CandidatoService candidatoService;

    @Autowired
    private CandidatoPublisher candidatoPublisher;

    @RabbitListener(queues = RabbitConfig.FILA_CANDIDATOS)
    public void criarCandidato(@Payload CandidatoDTO candidatoDTO) {
        try {
            // Armazena o candidato no banco
            candidatoService.criarCandidato(candidatoDTO);

            // Publica nova lista após adicionar novo candidato
            candidatoPublisher.publicarListaCandidatos();

            logger.info("Candidato criado e lista publicada: {}", candidatoDTO.toString());
        } catch (Exception e) {
            logger.error("Erro ao processar candidato: {}", candidatoDTO.toString(), e);
            throw e; // Re-lança para que o RabbitMQ possa tratar
        }
    }
}
