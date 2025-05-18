package com.eleicao.sd.service;

import com.eleicao.sd.dto.UsuarioDTO;
import com.eleicao.sd.entity.Usuario;
import com.eleicao.sd.repository.UsuarioRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final Logger logger = LogManager.getLogger(UserService.class);

    public Usuario createUser(UsuarioDTO usuarioDTO) {

        if (usuarioRepository.existsByNick(usuarioDTO.getNick())) {
            logger.error("Usuario ja existe: {}", usuarioDTO.getNick());
            throw new RuntimeException("nick ja existe");
        }
        Usuario usuario = new Usuario();
        usuario.setNick(usuarioDTO.getNick());

        logger.info("Criou user: {}", usuario.getNick());
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> buscarUsers() {
        return usuarioRepository.findAll();
    }

    public boolean existeUserByNick(String nick){
        return usuarioRepository.existsByNick(nick);
    }
}
