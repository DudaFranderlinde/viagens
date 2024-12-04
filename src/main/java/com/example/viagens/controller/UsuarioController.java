package com.example.viagens.controller;

import com.example.viagens.config.SecurityConfig;
import com.example.viagens.entity.Usuario;
import com.example.viagens.service.UsuarioService;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("criar")
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioCriado = usuarioService.criarUsuario(usuario);
            return new ResponseEntity<>(usuarioCriado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao criar usuário: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Optional<Usuario> autenticado = usuarioService.autenticarUsuario(usuario.getNome(), usuario.getSenha());
        
        if (autenticado.isPresent()) {
            return new ResponseEntity<>("", HttpStatus.OK );
        } else 
            return new ResponseEntity<>("Credenciais inválidas!", HttpStatus.UNAUTHORIZED);
        }
}