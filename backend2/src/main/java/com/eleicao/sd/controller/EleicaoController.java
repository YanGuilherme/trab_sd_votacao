package com.eleicao.sd.controller;

import com.eleicao.sd.entity.Candidato;
import com.eleicao.sd.entity.Usuario;
import com.eleicao.sd.service.EleicaoService;
import com.eleicao.sd.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eleicaoGP2")
public class EleicaoController {

    @Autowired
    private EleicaoService eleicaoService;

    @PostMapping("/token")
    public ResponseEntity<String> gerarToken(@RequestBody Usuario user) {
        if(eleicaoService.existeUserByNick(user.getNick())){
            String token = JwtUtil.generateToken(user.getNick());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().body("user nao existe");
    }


    @PostMapping("/user")
    public ResponseEntity<?> criarUser(@RequestBody Usuario user) {
        try {
            Usuario usuarioCriado = eleicaoService.createUser(user);
            String token = JwtUtil.generateToken(user.getNick());
            return ResponseEntity.status(201).body(token);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("nick ja existe")) {
                return ResponseEntity.status(400).body(e.getMessage());
            }
            return ResponseEntity.status(500).body("erro interno");
        }
    }


    @GetMapping("/user")
    public ResponseEntity<List<Usuario>> listarUsers(){
        List<Usuario> list = eleicaoService.buscarUsers();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/candidato")
    public ResponseEntity<?> criarCandidato(@RequestBody Candidato candidato){
        try {
            Candidato candidatoCriado = eleicaoService.createCandidato(candidato);
            return ResponseEntity.status(201).body(candidatoCriado);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("nome ja existe")) {
                return ResponseEntity.status(400).body(e.getMessage());
            }
            return ResponseEntity.status(500).body("erro interno");
        }
    }

    @GetMapping("/candidato")
    public ResponseEntity<List<Candidato>> listarCandidatos(){
        List<Candidato> list = eleicaoService.buscarCandidatos();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/votar/{candidatoId}")
    public ResponseEntity<String> votar(@RequestHeader("Authorization") String token, @PathVariable Long candidatoId) {
        try {
            String nick = JwtUtil.getNickFromToken(token.replace("Bearer ", ""));
            String resposta = eleicaoService.votar(nick, candidatoId);
            return ResponseEntity.ok(resposta);

        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido ou expirado");
        }
    }


    @GetMapping("/listarCandidatosDesc")
    public ResponseEntity<List<Candidato>> listarCandidatosDesc(){
        List<Candidato> lista = eleicaoService.listarPorQuantidadeVotosDesc();
        return ResponseEntity.ok(lista);
    }


}
