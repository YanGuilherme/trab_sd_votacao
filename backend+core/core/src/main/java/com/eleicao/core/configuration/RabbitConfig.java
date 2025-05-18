package com.eleicao.core.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    public static final String FILA_VOTOS = "fila-votos";
    public static final String FILA_CANDIDATOS = "fila-candidatos";
    public static final String FILA_WEBSOCKET_CANDIDATOS = "websocket-candidatos-fila";


    private static final Logger logger = LogManager.getLogger(RabbitConfig.class);


    @Bean
    public Queue filaVotos() {
        return new Queue(FILA_VOTOS, true);
    }

    @Bean
    public Queue filaCandidatos(){
        return new Queue(FILA_CANDIDATOS, true);
    }

    @Bean
    public Queue filaWebSocketCandidatos(){
        return new Queue(FILA_WEBSOCKET_CANDIDATOS, true);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @Primary
    public MessageConverter jacksonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("*"); // permite qualquer pacote

        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.eleicao.sd.dto.VotoDTO", com.eleicao.core.dto.VotoDTO.class);
        typeMapper.setIdClassMapping(idClassMapping);

        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setDefaultRequeueRejected(false);

        factory.setErrorHandler(new ConditionalRejectingErrorHandler(t -> {
            logger.error("Erro no processamento da mensagem: {}", t.getCause().getMessage());
            return false;
        }));
        return factory;
    }

    @Bean
    public FanoutExchange candidatosFanoutExchange() {
        return new FanoutExchange("exchange-candidatos");
    }

    @Bean
    public Binding candidatosWebSocketBinding() {
        return BindingBuilder.bind(filaWebSocketCandidatos())
                .to(candidatosFanoutExchange());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jacksonMessageConverter());
        return template;
    }

}
