package com.john.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.john.minhasfinancas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
