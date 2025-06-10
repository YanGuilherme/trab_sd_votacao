package com.eleicao.core.component;

import com.eleicao.core.dto.CidadeDTO;
import com.eleicao.core.entity.Cidade;
import com.eleicao.core.service.CandidatoService;
import com.eleicao.core.service.CidadeService;
import com.eleicao.core.service.MensagemService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CidadePublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CidadeService cidadeService;


    public void publicarCidades() {

        List<CidadeDTO> cidades = cidadeService.listAll();
        rabbitTemplate.convertAndSend("websocket-cidades-fila", "", cidades);
    }
}

