package com.reporte.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "incidentes")
@Data
@NoArgsConstructor
public class Incidente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;

	@Column(length = 500)
	private String descripcion;

	private Double latitud;

	private Double longitud;

	private LocalDateTime fecha;
	
	private String fotoUrl;

	@Enumerated(EnumType.STRING)
	private TipoIncidente tipoIncidente;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@Column(columnDefinition = "integer default 0")
	private Integer cantidadVotos = 0;

}