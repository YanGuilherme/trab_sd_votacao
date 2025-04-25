package com.eleicao.sd.repository;

import com.eleicao.sd.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByNick(String nick);
}
