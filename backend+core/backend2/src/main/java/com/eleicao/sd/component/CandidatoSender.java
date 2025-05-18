package com.eleicao.sd.component;


import com.eleicao.sd.configuration.RabbitConfig;
import com.eleicao.sd.dto.CandidatoDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;


@Component
public class CandidatoSender {
    private final AmqpTemplate amqpTemplate;

    public CandidatoSender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    private static final Logger logger = LogManager.getLogger(CandidatoSender.class);

    public void criarCandidato(CandidatoDTO candidatoDTO){
        amqpTemplate.convertAndSend(RabbitConfig.FILA_CANDIDATOS, candidatoDTO);
        logger.info("Candidato criado: {}", candidatoDTO.toString());
    }

}
