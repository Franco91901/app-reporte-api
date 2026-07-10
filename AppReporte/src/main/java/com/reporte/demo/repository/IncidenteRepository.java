package com.reporte.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reporte.demo.entity.Incidente;
import com.reporte.demo.entity.TipoIncidente;

public interface IncidenteRepository extends JpaRepository<Incidente, Long> {

    List<Incidente> findByTipoIncidente(TipoIncidente tipoIncidente);
    
    List<Incidente> findByUsuarioId(Long usuarioId);
    
    List<Incidente> findByTipoIncidenteOrderByFechaDesc(TipoIncidente tipoIncidente);

    @Query(value = "SELECT * FROM incidentes i WHERE " +
    	       "(6371 * acos(cos(radians(:lat)) * cos(radians(i.latitud)) * " +
    	       "cos(radians(i.longitud) - radians(:lng)) + sin(radians(:lat)) * " +
    	       "sin(radians(i.latitud)))) <= :radio", nativeQuery = true)
    	List<Incidente> buscarCercanos(@Param("lat") Double lat, 
    	                               @Param("lng") Double lng, 
    	                               @Param("radio") Double radio);
    
    @Query(value = "SELECT * FROM incidentes i WHERE " +
    	       "(:tipo = 'TODOS' OR i.tipo_incidente = :tipo) AND " +
    	       "(6371 * acos(cos(radians(:lat)) * cos(radians(i.latitud)) * " +
    	       "cos(radians(i.longitud) - radians(:lng)) + sin(radians(:lat)) * " +
    	       "sin(radians(i.latitud)))) <= :radio " +
    	       "ORDER BY i.cantidad_votos DESC, i.fecha DESC", nativeQuery = true)
    	List<Incidente> buscarAvanzado(@Param("lat") Double lat, 
    	                               @Param("lng") Double lng, 
    	                               @Param("radio") Double radio,
    	                               @Param("tipo") String tipo);

    List<Incidente> findAllByOrderByCantidadVotosDescFechaDesc();
}