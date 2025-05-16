package com.eleicao.sd.configuration;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfig {
    public static final String NOME_FILA = "fila-votos";

    @Bean
    public Queue filaVotos() {
        return new Queue(NOME_FILA, true);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter(); // usa Jackson para converter objetos JSON
    }

}

