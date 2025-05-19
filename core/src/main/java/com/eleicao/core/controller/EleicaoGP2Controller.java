package com.eleicao.core.controller;

import com.eleicao.core.component.CandidatoPublisher;
import com.eleicao.core.entity.Candidato;
import com.eleicao.core.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/eleicao-gp2")
public class EleicaoGP2Controller {

    @Autowired
    private CandidatoService candidatoService;

    @Autowired
    private CandidatoPublisher candidatoPublisher;

    @GetMapping("/listarCandidatosDesc")
    public ResponseEntity<List<Candidato>> listarCandidatosDesc(){
        List<Candidato> lista = candidatoService.listarPorQuantidadeVotosDesc();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Candidato>> listAll(){
        List<Candidato> lista = candidatoService.listarCandidatos();
        return ResponseEntity.ok(lista);
    }

    // Endpoint para forçar uma atualização via WebSocket
    @PostMapping("/atualizar-candidatos")
    public ResponseEntity<String> atualizarCandidatos() {
        try {
            candidatoPublisher.publicarListaCandidatos();
            return ResponseEntity.ok("Lista de candidatos atualizada via WebSocket");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar");
        }
    }
}
