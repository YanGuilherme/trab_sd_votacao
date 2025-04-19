package com.trabalhosd.votacao.controller;

import com.trabalhosd.votacao.dto.ComprovanteVotacaoDTO;
import com.trabalhosd.votacao.dto.EleitorDTO;
import com.trabalhosd.votacao.dto.VotarDTO;
import com.trabalhosd.votacao.entity.Candidato;
import com.trabalhosd.votacao.entity.Eleicao;
import com.trabalhosd.votacao.entity.Eleitor;
import com.trabalhosd.votacao.service.CandidatoService;
import com.trabalhosd.votacao.service.EleicaoService;
import com.trabalhosd.votacao.service.EleitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/eleitor")

public class EleitorController {


    @Autowired
    private CandidatoService candidatoService;

    @Autowired
    private EleicaoService eleicaoService;

    @Autowired
    private EleitorService eleitorService;

    @PostMapping("/votar")
    public ResponseEntity<?> votar(@RequestBody VotarDTO votarDTO){
        Eleitor eleitor = eleitorService.buscarPorId(votarDTO.getId_eleitor());
        if(eleitor.getVoto() != null){
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensagem", "Seu voto já foi computado"));
        }

        Candidato candidato = candidatoService.findById(votarDTO.getCandidato_id());
        candidato.setQuantidade_votos(candidato.getQuantidade_votos() + 1);
        candidatoService.salvar(candidato);

        Eleicao eleicao = eleicaoService.getById(candidato.getEleicao().getId());
        eleitor.setVoto(candidato);
        eleitorService.salvar(eleitor);

        ComprovanteVotacaoDTO comprovanteVotacaoDTO = new ComprovanteVotacaoDTO(candidato.getNome(), eleicao.getTitulo());
        return ResponseEntity.ok(comprovanteVotacaoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EleitorDTO> buscarPorId(@PathVariable String id) {
        EleitorDTO eleitorDTO = eleitorService.convertToDTO(eleitorService.buscarPorId(id));

        if (eleitorDTO != null) {
            return ResponseEntity.ok(eleitorDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<EleitorDTO> criar(@RequestBody EleitorDTO eleitorDTO) {
        EleitorDTO eleitorCriado = eleitorService.criar(eleitorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(eleitorCriado);
    }
}
