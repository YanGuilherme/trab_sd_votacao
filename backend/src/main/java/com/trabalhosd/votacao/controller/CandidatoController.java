package com.trabalhosd.votacao.controller;

import com.trabalhosd.votacao.dto.CandidatoDTO;
import com.trabalhosd.votacao.entity.Candidato;
import com.trabalhosd.votacao.exception.CandidatoNotFoundException;
import com.trabalhosd.votacao.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/candidato")

public class CandidatoController {

    @Autowired
    private CandidatoService candidatoService;

    @GetMapping("/{id}")
    public ResponseEntity<CandidatoDTO> create(@PathVariable String id){
        CandidatoDTO candidato = candidatoService.findById(id);

        return ResponseEntity.ok(candidato);
    }

    @PostMapping("/create")
    public ResponseEntity<CandidatoDTO> create(@RequestBody CandidatoDTO candidatoDTO){
        CandidatoDTO createdCandidato = candidatoService.create(candidatoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCandidato);
    }

    @GetMapping("/list")
    public List<Candidato> listarCandidatos() {
        return candidatoService.listarCandidatos();
    }


    @GetMapping("/eleicao/{id_eleicao}")
    public List<CandidatoDTO> listarCandidatosPorEleicao(@PathVariable String id_eleicao){
        return candidatoService.listarCandidatosPorEleicao(id_eleicao);
    }

    @DeleteMapping("/delete/{id_delete}")
    public ResponseEntity<Void> deletarCandidato(@PathVariable String id_delete){
        candidatoService.deleteCandidato(id_delete);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CandidatoNotFoundException.class)
    public ResponseEntity<Object> handleCandidatoNotFoundException(CandidatoNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}