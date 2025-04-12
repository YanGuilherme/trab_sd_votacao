package com.trabalhosd.votacao.service;

import com.trabalhosd.votacao.dto.EleicaoDTO;
import com.trabalhosd.votacao.entity.Eleicao;
import com.trabalhosd.votacao.exception.EleicaoNotFoundException;
import com.trabalhosd.votacao.repository.EleicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EleicaoService {

    @Autowired
    private EleicaoRepository eleicaoRepository;

    public Eleicao getById(String id) {
        return eleicaoRepository.findById(id)
                .orElseThrow(EleicaoNotFoundException::new);
    }

    public List<Eleicao> getAll(){
        return eleicaoRepository.findAll();
    }

    public Eleicao create(EleicaoDTO eleicaoDTO) {
        Eleicao eleicao = new Eleicao();
        eleicao.setTitulo(eleicaoDTO.getTitulo());
        eleicao.setDescricao(eleicaoDTO.getDescricao());
        return eleicaoRepository.save(eleicao);
    }

    public void delete(String id) {
        if (!eleicaoRepository.existsById(id)) {
            throw new EleicaoNotFoundException();
        }
        eleicaoRepository.deleteById(id);
    }


}
