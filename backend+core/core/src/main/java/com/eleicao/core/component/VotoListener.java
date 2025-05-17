package com.eleicao.core.component;

import com.eleicao.core.dto.VotoDTO;
import com.eleicao.core.entity.Voto;
import com.eleicao.core.repository.VotoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class VotoListener {

    private final VotoRepository votoRepository;

    public VotoListener(VotoRepository votoRepository) {
        this.votoRepository = votoRepository;
    }

    @RabbitListener(queues = "fila-votos")
    public void processarVoto(@Payload VotoDTO votoDTO) {
        System.out.println("Voto recebido: " + votoDTO.toString());

        Voto voto = new Voto();
        voto.setCandidato_id(votoDTO.getCandidato_id());
        voto.setQuantidade_votos(votoDTO.getQuantidade_votos());
        votoRepository.save(voto);
    }
}
