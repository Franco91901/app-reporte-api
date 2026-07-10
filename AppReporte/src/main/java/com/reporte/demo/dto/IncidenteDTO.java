package com.reporte.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class IncidenteDTO {

	private Long id;

	private String titulo;

	private String descripcion;

	private Double latitud;

	private Double longitud;

	private String tipo;

	private LocalDateTime fecha;
	
	private String fotoUrl;

	private String usuarioNombres;

	private String usuarioApellidos;

	private Integer cantidadVotos;
}
