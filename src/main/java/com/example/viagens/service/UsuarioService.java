package com.example.viagens.service;

import com.example.viagens.entity.Usuario;
import com.example.viagens.repository.UsuarioRepository;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public  UsuarioService (UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criarUsuario(Usuario usuario) {  
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> autenticarUsuario(String username, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByNome(username);
        System.err.println(usuario.isPresent());
        if (usuario.isPresent()) {
            System.err.println(usuario.get().getSenha().matches(senha));

            

            if (usuario.get().getSenha().matches(senha)) {
                return usuario; 
            }
        }
        return Optional.empty(); 
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNome(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return User.builder()
                .username(usuario.getNome())
                .password(usuario.getSenha())
                .build();
    }
}

