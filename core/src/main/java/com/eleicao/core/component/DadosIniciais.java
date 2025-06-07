package com.eleicao.core.component;

import com.eleicao.core.entity.Candidato;
import com.eleicao.core.repository.CandidatoRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DadosIniciais implements CommandLineRunner {

    @Autowired
    private CandidatoRepository candidatoRepository;

    private static final Logger logger = LogManager.getLogger(DadosIniciais.class);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        logger.info("Criando a base de candidatos.");
        if (candidatoRepository.count() == 0) {
            logger.info("Criou base de candidatos.");
            candidatoRepository.save(new Candidato("Pedro Damaso", 0L, lerImagem("/imagens/1.png")));
            candidatoRepository.save(new Candidato("Romário", 0L, lerImagem("/imagens/2.png")));
            candidatoRepository.save(new Candidato("Tirica", 0L, lerImagem("/imagens/3.png")));
            candidatoRepository.save(new Candidato("Prefeito de Sorocaba-SP", 0L, lerImagem("/imagens/4.png")));
            candidatoRepository.save(new Candidato("Pastor Mirim", 0L, lerImagem("/imagens/5.png")));
        }
    }

    private byte[] lerImagem(String caminho) throws IOException {
        ClassPathResource imgFile = new ClassPathResource(caminho);
        return imgFile.getInputStream().readAllBytes();
    }
}
