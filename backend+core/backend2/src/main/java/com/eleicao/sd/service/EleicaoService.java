package com.eleicao.sd.service;

import com.eleicao.sd.entity.Candidato;
import com.eleicao.sd.entity.Usuario;
import com.eleicao.sd.repository.CandidatoRepository;
import com.eleicao.sd.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EleicaoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    public boolean existeUserByNick(String nick){
        return usuarioRepository.existsByNick(nick);
    }

    public Usuario createUser(Usuario usuario) {
        if (usuarioRepository.existsByNick(usuario.getNick())) {
            throw new RuntimeException("nick ja existe");
        }
        return usuarioRepository.save(usuario);
    }

    public Candidato createCandidato(Candidato candidato){
        if (candidatoRepository.existsByNome(candidato.getNome())){
            throw new RuntimeException("nome ja existe");
        }
        return candidatoRepository.save(candidato);
    }

    public List<Candidato> listarCandidatos(){
        return candidatoRepository.findAll();
    }

    public List<Candidato> listarPorQuantidadeVotosDesc(){
        return candidatoRepository.findAllByOrderByQuantidadeVotosDesc();
    }



    public String votar(String nick, Long id) {
        if (!usuarioRepository.existsByNick(nick)) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Candidato candidato = candidatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidato não encontrado"));

        candidato.setQuantidadeVotos(candidato.getQuantidadeVotos() + 1);
        candidatoRepository.save(candidato);

        return "Votou em " + candidato.getNome();
    }

    public List<Usuario> buscarUsers() {
        return usuarioRepository.findAll();
    }

    public List<Candidato> buscarCandidatos(){
        return candidatoRepository.findAll();
    }
}
