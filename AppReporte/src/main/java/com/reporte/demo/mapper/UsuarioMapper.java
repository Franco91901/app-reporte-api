package com.reporte.demo.mapper;

import org.springframework.stereotype.Component;

import com.reporte.demo.dto.UsuarioDTO;
import com.reporte.demo.entity.Usuario;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario usuario) {

        UsuarioDTO dto = new UsuarioDTO();

        dto.setId(usuario.getId());
        dto.setNombres(usuario.getNombres());
        dto.setApellidos(usuario.getApellidos());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol().name());

        return dto;
    }

}