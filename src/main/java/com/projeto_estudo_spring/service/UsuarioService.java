package com.projeto_estudo_spring.service;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projeto_estudo_spring.model.Usuario;
import com.projeto_estudo_spring.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Usuario registrarUsuario(String username, String password){
        String senhaCriptografada = passwordEncoder.encode(password);
        Usuario usuario = new Usuario(username, senhaCriptografada);
        return repository.save(usuario);

    }

    public Optional<Usuario> buscarPorUsername(String username){
        return repository.findByUsername(username);
    }
}
