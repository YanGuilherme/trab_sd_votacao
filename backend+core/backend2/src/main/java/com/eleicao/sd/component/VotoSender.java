package com.eleicao.sd.component;

import com.eleicao.sd.configuration.RabbitConfig;
import com.eleicao.sd.dto.VotoDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
public class VotoSender {

    private final AmqpTemplate amqpTemplate;

    public VotoSender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void enviarVoto(VotoDTO voto) {
        amqpTemplate.convertAndSend(RabbitConfig.NOME_FILA, voto);
        System.out.println("Voto enviado: " + voto.toString());
    }
}

