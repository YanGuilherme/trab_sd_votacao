package com.eleicao.sd.service;

import com.eleicao.sd.component.CandidatoSender;
import com.eleicao.sd.component.VotoSender;
import com.eleicao.sd.dto.CandidatoDTO;
import com.eleicao.sd.dto.VotoDTO;
import com.eleicao.sd.repository.UsuarioRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EleicaoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final Logger logger = LogManager.getLogger(EleicaoService.class);

    private final VotoSender votoSender;
    private final CandidatoSender candidatoSender;

    public EleicaoService(VotoSender votoSender, CandidatoSender candidatoSender) {
        this.votoSender = votoSender;
        this.candidatoSender = candidatoSender;
    }


    public void createCandidato(CandidatoDTO candidatoDTO){
        logger.info("Enviou candidato para ser criado: {}", candidatoDTO.getNome());
        candidatoSender.criarCandidato(candidatoDTO);
    }

    public String votar(String nick, Long id_candidato) {
        if (!usuarioRepository.existsByNick(nick)) {
            logger.error("Erro ao votar - User nao encontrado: {}", nick);
            throw new RuntimeException("Usuário não encontrado");
        }


        logger.info("Candidato encontrado - id: {}", id_candidato);

        LocalDateTime agora = LocalDateTime.now();

        VotoDTO voto = new VotoDTO();
        voto.setType("eleicao-gp2");
        voto.setObject(id_candidato.toString()); //passando o id do candidato
        voto.setValor(1L);
        voto.setTimestamp(agora);

        votoSender.enviarVoto(voto);
        logger.info("Voto para o candidato: {}", id_candidato.toString());
        return "Votou em " + id_candidato.toString();
    }
}
