package com.reporte.demo.dto;

import lombok.Data;

@Data
public class CrearIncidenteDTO {

    private String titulo;

    private String descripcion;

    private Double latitud;

    private Double longitud;

    private String tipo;

}
