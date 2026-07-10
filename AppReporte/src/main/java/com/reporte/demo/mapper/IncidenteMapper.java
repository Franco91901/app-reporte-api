package com.reporte.demo.mapper;

import org.springframework.stereotype.Component;

import com.reporte.demo.dto.IncidenteDTO;
import com.reporte.demo.entity.Incidente;

@Component
public class IncidenteMapper {

    public IncidenteDTO toDTO(Incidente incidente) {

        IncidenteDTO dto = new IncidenteDTO();

        dto.setId(incidente.getId());
        dto.setTitulo(incidente.getTitulo());
        dto.setDescripcion(incidente.getDescripcion());
        dto.setLatitud(incidente.getLatitud());
        dto.setLongitud(incidente.getLongitud());
        dto.setFecha(incidente.getFecha());
        dto.setTipo(incidente.getTipoIncidente().name());
        dto.setFotoUrl(incidente.getFotoUrl());
        dto.setUsuarioNombres(incidente.getUsuario().getNombres());
        dto.setUsuarioApellidos(incidente.getUsuario().getApellidos());
        dto.setCantidadVotos(incidente.getCantidadVotos());

        return dto;
    }

}