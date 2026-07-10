package com.reporte.demo.mapper;

import org.springframework.stereotype.Component;

import com.reporte.demo.dto.ImagenIncidenteDTO;
import com.reporte.demo.entity.ImagenIncidente;

@Component
public class ImagenIncidenteMapper {

    public ImagenIncidenteDTO toDTO(ImagenIncidente imagen) {

        ImagenIncidenteDTO dto = new ImagenIncidenteDTO();

        dto.setId(imagen.getId());
        dto.setUrl(imagen.getUrl());

        return dto;
    }

}