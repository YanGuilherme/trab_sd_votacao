package com.eleicao.sd.service;

import com.eleicao.sd.component.CandidatoSender;
import com.eleicao.sd.component.VotoSender;
import com.eleicao.sd.dto.CandidatoDTO;
import com.eleicao.sd.dto.VotoDTO;
import com.eleicao.sd.dto.UsuarioDTO;
import com.eleicao.sd.entity.Usuario;
import com.eleicao.sd.repository.UsuarioRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        logger.info("Criou candidato: {}", candidatoDTO.getNome());
        candidatoSender.criarCandidato(candidatoDTO);
    }

    public String votar(String nick, String nome_candidato) {
        if (!usuarioRepository.existsByNick(nick)) {
            logger.error("Erro ao votar - User nao encontrado: {}", nick);
            throw new RuntimeException("Usuário não encontrado");
        }


        logger.info("Candidato encontrado: {}", nome_candidato);

        LocalDateTime agora = LocalDateTime.now();

        VotoDTO voto = new VotoDTO();
        voto.setType("eleicao-gp2");
        voto.setObject(nome_candidato);
        voto.setValor(1L);
        voto.setTimestamp(agora);

        votoSender.enviarVoto(voto);
        logger.info("Voto para o candidato: {}", nome_candidato);
        return "Votou em " + nome_candidato;
    }
}
