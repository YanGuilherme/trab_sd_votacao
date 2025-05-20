package com.eleicao.sd.controller;

import com.eleicao.sd.dto.UsuarioDTO;
import com.eleicao.sd.entity.Usuario;
import com.eleicao.sd.service.EleicaoService;
import com.eleicao.sd.service.UserService;
import com.eleicao.sd.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/token")
    public ResponseEntity<String> gerarToken(@RequestBody Usuario user) {
        if(userService.existeUserByNick(user.getNick())){
            String token = JwtUtil.generateToken(user.getNick());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().body("user nao existe");
    }


    @PostMapping
    public ResponseEntity<?> criarUser(@RequestBody UsuarioDTO user) {
        try {
            Usuario usuarioCriado = userService.createUser(user);
            String token = JwtUtil.generateToken(user.getNick());
            return ResponseEntity.status(201).body(token);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("nick ja existe")) {
                return ResponseEntity.status(400).body(e.getMessage());
            }
            return ResponseEntity.status(500).body("erro interno");
        }
    }


    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsers(@RequestHeader("Authorization") String token){
        String nick = JwtUtil.getNickFromToken(token.replace("Bearer ", ""));
        if(userService.existeUserByNick(nick)){
            List<Usuario> list = userService.buscarUsers();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.status(401).build();
    }
}
