package com.reporte.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.reporte.demo.dto.CrearIncidenteDTO;
import com.reporte.demo.dto.IncidenteDTO;
import com.reporte.demo.entity.TipoIncidente;

public interface IncidenteService {
	IncidenteDTO crearIncidenteConEmail(CrearIncidenteDTO dto, String email);

	IncidenteDTO crearIncidente(CrearIncidenteDTO dto, Long usuarioId);

	List<IncidenteDTO> listarIncidentes();

	List<IncidenteDTO> listarIncidentesPorTipo(TipoIncidente tipo);

	List<IncidenteDTO> listarIncidentesPorUsuario(Long usuarioId);

	IncidenteDTO obtenerIncidente(Long id);

	void eliminarIncidente(Long id);

	String subirFoto(Long incidenteId, MultipartFile file);

	List<IncidenteDTO> buscarAvanzado(Double lat, Double lng, Double radio, String tipo);

	boolean votarIncidente(Long incidenteId, String email);

	long contarVotos(Long incidenteId);

	boolean usuarioHaVotado(Long incidenteId, String email);
}
