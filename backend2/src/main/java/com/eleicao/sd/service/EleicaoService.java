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

    public Usuario createUser(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Candidato createCandidato(Candidato candidato){
        return candidatoRepository.save(candidato);
    }

    public List<Candidato> listarCandidatos(){
        return candidatoRepository.findAll();
    }

    public List<Candidato> listarPorQuantidadeVotosDesc(){
        return candidatoRepository.findAllByOrderByQuantidadeVotosDesc();
    }



    public String votar(Long id){
        Candidato candidato = candidatoRepository.findById(id).orElse(null);
        if(candidato != null){
            candidato.setQuantidadeVotos(candidato.getQuantidadeVotos() + 1);
            candidatoRepository.save(candidato);
            return "Votou";
        }
        return "Não votou";

    }
}
