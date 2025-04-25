package com.eleicao.sd.controller;

import com.eleicao.sd.entity.Candidato;
import com.eleicao.sd.entity.Usuario;
import com.eleicao.sd.service.EleicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eleicaoGP2")
public class EleicaoController {

    @Autowired
    private EleicaoService eleicaoService;

    @PostMapping("/createUser")
    public ResponseEntity<Usuario> criarUser(@RequestBody Usuario user){
        Usuario usuarioCriado = eleicaoService.createUser(user);
        return ResponseEntity.status(201).body(usuarioCriado);

    }

    @PostMapping("/createCandidato")
    public ResponseEntity<Candidato> criarCandidato(@RequestBody Candidato candidato){
        Candidato candidatoCriado = eleicaoService.createCandidato(candidato);
        return ResponseEntity.status(201).body(candidato);
    }

    @PostMapping("votar/{candidatoId}")
    public ResponseEntity<String> votar(@PathVariable Long candidatoId){
        String response = eleicaoService.votar(candidatoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listarCandidatosDesc")
    public ResponseEntity<List<Candidato>> listarCandidatosDesc(){
        List<Candidato> lista = eleicaoService.listarPorQuantidadeVotosDesc();
        return ResponseEntity.ok(lista);
    }
}
