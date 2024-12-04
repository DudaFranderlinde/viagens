package com.example.viagens.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.viagens.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNome(String nome);
}
