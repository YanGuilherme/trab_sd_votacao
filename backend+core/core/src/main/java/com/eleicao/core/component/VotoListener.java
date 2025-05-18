package com.eleicao.core.component;

import com.eleicao.core.configuration.RabbitConfig;
import com.eleicao.core.dto.VotoDTO;
import com.eleicao.core.repository.VotoRepository;
import com.eleicao.core.service.VotoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class VotoListener {
    private static final Logger logger = LogManager.getLogger(VotoListener.class);

    @Autowired
    private VotoService votoService;

    @Autowired
    private CandidatoPublisher candidatoPublisher;

    @RabbitListener(queues = RabbitConfig.FILA_VOTOS)
    public void processarVoto(@Payload VotoDTO votoDTO) {
        try {
            votoService.processarVoto(votoDTO);
            candidatoPublisher.publicarListaCandidatos();
            logger.info("Voto processado e lista atualizada: {}", votoDTO.toString());
        } catch (Exception e) {
            logger.error("Erro ao processar voto: {}", votoDTO.toString(), e);
            throw e;
        }
    }

}
