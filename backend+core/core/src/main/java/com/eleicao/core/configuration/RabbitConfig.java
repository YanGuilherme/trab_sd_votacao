package com.eleicao.core.configuration;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfig {
    public static final String FILA_VOTOS = "fila-votos";
    public static final String FILA_CANDIDATOS = "fila-candidatos";


    @Bean
    public Queue filaVotos() {
        return new Queue(FILA_VOTOS, true);
    }

    @Bean
    public Queue filaCandidatos(){
        return new Queue(FILA_CANDIDATOS, true);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
