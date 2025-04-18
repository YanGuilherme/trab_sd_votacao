package com.trabalhosd.votacao.controller;

import com.trabalhosd.votacao.dto.EleicaoDTO;
import com.trabalhosd.votacao.entity.Eleicao;
import com.trabalhosd.votacao.exception.EleicaoNotFoundException;
import com.trabalhosd.votacao.service.EleicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eleicao")
public class EleicaoController {

    @Autowired
    private EleicaoService eleicaoService;

    @PostMapping("/create")
    public ResponseEntity<EleicaoDTO> create(@RequestBody EleicaoDTO eleicaoDTO) {
        Eleicao eleicao = eleicaoService.create(eleicaoDTO);
        EleicaoDTO eleicaoDto = new EleicaoDTO(eleicao.getId(), eleicao.getTitulo(), eleicao.getDescricao());
        return ResponseEntity.status(HttpStatus.CREATED).body(eleicaoDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Eleicao> getById(@PathVariable String id) {
        Eleicao eleicao = eleicaoService.getById(id);
        return ResponseEntity.ok(eleicao);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Eleicao>> getAll(){
        return ResponseEntity.ok(eleicaoService.getAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        eleicaoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(EleicaoNotFoundException.class)
    public ResponseEntity<Object> handleEleicaoNotFoundException(EleicaoNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
