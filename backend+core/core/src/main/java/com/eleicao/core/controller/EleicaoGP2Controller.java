package com.eleicao.core.controller;

import com.eleicao.core.entity.Candidato;
import com.eleicao.core.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/eleicao-gp2")
public class EleicaoGP2Controller {

    @Autowired
    private CandidatoService candidatoService;

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
}
