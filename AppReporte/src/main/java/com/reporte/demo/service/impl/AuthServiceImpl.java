package com.reporte.demo.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reporte.demo.dto.AuthResponse;
import com.reporte.demo.dto.LoginDTO;
import com.reporte.demo.dto.RegistroDTO;
import com.reporte.demo.dto.UsuarioDTO;
import com.reporte.demo.entity.Rol;
import com.reporte.demo.entity.Usuario;
import com.reporte.demo.mapper.UsuarioMapper;
import com.reporte.demo.repository.UsuarioRepository;
import com.reporte.demo.security.JwtService;
import com.reporte.demo.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioDTO register(RegistroDTO request) {

        if(usuarioRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();

        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Rol.ROLE_USER);
        usuario.setFechaRegistro(LocalDateTime.now());

        Usuario guardado = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(guardado);
    }

    @Override
    public AuthResponse login(LoginDTO request){

        Usuario usuario = usuarioRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if(!passwordEncoder.matches(request.getPassword(), usuario.getPassword())){
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtService.generateToken(
                usuario.getEmail(),
                usuario.getRol().name()
        );

        return new AuthResponse(token);
    }
}
