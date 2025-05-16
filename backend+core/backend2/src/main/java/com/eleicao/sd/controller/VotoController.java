package com.eleicao.sd.controller;

import com.eleicao.sd.component.VotoSender;
import com.eleicao.sd.dto.VotoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/votos")
public class VotoController {

    private final VotoSender sender;

    public VotoController(VotoSender sender) {
        this.sender = sender;
    }

    @PostMapping
    public ResponseEntity<Void> votar(@RequestBody VotoDTO voto) {
        sender.enviarVoto(voto);
        return ResponseEntity.ok().build();
    }
}
