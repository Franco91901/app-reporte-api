package com.reporte.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reporte.demo.entity.ImagenIncidente;

public interface ImagenIncidenteRepository extends JpaRepository<ImagenIncidente, Long> {

    List<ImagenIncidente> findByIncidenteId(Long incidenteId);

}
