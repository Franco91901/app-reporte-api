package com.reporte.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reporte.demo.entity.VotoIncidente;

import java.util.Optional;

@Repository
public interface VotoIncidenteRepository extends JpaRepository<VotoIncidente, Long> {

    Optional<VotoIncidente> findByUsuarioIdAndIncidenteId(Long usuarioId, Long incidenteId);

    boolean existsByUsuarioIdAndIncidenteId(Long usuarioId, Long incidenteId);

    long countByIncidenteId(Long incidenteId);
}
