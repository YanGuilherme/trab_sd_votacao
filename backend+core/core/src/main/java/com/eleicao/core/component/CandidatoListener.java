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

    @RabbitListener(queues = RabbitConfig.FILA_CANDIDATOS)
    public void criarCandidato(@Payload CandidatoDTO candidatoDTO) {
        candidatoService.criarCandidato(candidatoDTO);
        logger.info("Candidato criado: {}", candidatoDTO.toString());

    }
}
