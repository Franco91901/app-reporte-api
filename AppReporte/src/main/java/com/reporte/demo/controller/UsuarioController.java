package com.reporte.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reporte.demo.dto.UsuarioDTO;
import com.reporte.demo.entity.Usuario;
import com.reporte.demo.mapper.UsuarioMapper;
import com.reporte.demo.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDTO> obtenerPerfil() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(usuarioMapper.toDTO(usuario));
    }
}