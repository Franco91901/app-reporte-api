package com.reporte.demo.dto;

import lombok.Data;

@Data
public class UsuarioDTO {

    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private String rol;

}
