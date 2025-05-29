package com.eleicao.sd.controller;

import com.eleicao.sd.dto.CandidatoDTO;
import com.eleicao.sd.service.EleicaoService;
import com.eleicao.sd.service.UserService;
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

    @Autowired
    private UserService userService;

    @Transactional
    @PostMapping("/votar/{id_candidato}")
    public ResponseEntity<String> votar(@RequestHeader("Authorization") String token, @PathVariable Long id_candidato) {
        try {
            String nick = JwtUtil.getNickFromToken(token.replace("Bearer ", ""));
            String resposta = eleicaoService.votar(nick, id_candidato);
            return ResponseEntity.ok(resposta);

        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido ou expirado");
        }
    }

    @PostMapping("/candidato")
    public ResponseEntity<?> criarCandidato(@RequestHeader("Authorization") String token, @RequestBody CandidatoDTO candidato){
        String nick = JwtUtil.getNickFromToken(token.replace("Bearer ", ""));
        if(userService.existeUserByNick(nick)){
            eleicaoService.createCandidato(candidato);
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(401).build();

    }
}
