package com.eleicao.sd.controller;

import com.eleicao.sd.dto.CandidatoDTO;
import com.eleicao.sd.service.EleicaoService;
import com.eleicao.sd.utils.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eleicao-gp2")
public class EleicaoController {

    @Autowired
    private EleicaoService eleicaoService;

    @Transactional
    @PostMapping("/votar/{nome_candidato}")
    public ResponseEntity<String> votar(@RequestHeader("Authorization") String token, @PathVariable String nome_candidato) {
        try {
            String nick = JwtUtil.getNickFromToken(token.replace("Bearer ", ""));
            String resposta = eleicaoService.votar(nick, nome_candidato);
            return ResponseEntity.ok(resposta);

        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido ou expirado");
        }
    }

    @PostMapping("/candidato")
    public ResponseEntity<?> criarCandidato(@RequestBody CandidatoDTO candidato){
        try {
            eleicaoService.createCandidato(candidato);
            return ResponseEntity.status(201).build();
        } catch (RuntimeException e) {
            if (e.getMessage().equals("nome ja existe")) {
                return ResponseEntity.status(400).body(e.getMessage());
            }
            return ResponseEntity.status(500).body("erro interno");
        }
    }
}
