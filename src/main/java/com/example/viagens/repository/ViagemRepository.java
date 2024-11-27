package com.example.viagens.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.viagens.entity.Viagem;

import java.util.List;

public interface ViagemRepository extends JpaRepository<Viagem, Long> {
    List<Viagem> findByNomeContainingIgnoreCase(String nome);
    List<Viagem> findByLocalizacaoContainingIgnoreCase(String localizacao);
}
