package com.reporte.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reporte.demo.entity.VotoIncidente;

@Repository
public interface VotoIncidenteRepository extends JpaRepository<VotoIncidente, Long> {

    boolean existsByUsuarioIdAndIncidenteId(Long usuarioId, Long incidenteId);

    long countByIncidenteId(Long incidenteId);
}
