package com.reporte.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.reporte.demo.dto.CrearIncidenteDTO;
import com.reporte.demo.dto.IncidenteDTO;
import com.reporte.demo.entity.TipoIncidente;
import com.reporte.demo.service.IncidenteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/incidentes")
@RequiredArgsConstructor
public class IncidenteController {

    private final IncidenteService incidenteService;

    @GetMapping
    public ResponseEntity<List<IncidenteDTO>> listarIncidentes(){

        return ResponseEntity.ok(incidenteService.listarIncidentes());

    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<IncidenteDTO>> listarPorTipo(@PathVariable TipoIncidente tipo){

        return ResponseEntity.ok(incidenteService.listarIncidentesPorTipo(tipo));

    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidenteDTO> obtenerIncidente(@PathVariable Long id){

        return ResponseEntity.ok(incidenteService.obtenerIncidente(id));

    }

    @PostMapping
    public ResponseEntity<IncidenteDTO> crearIncidente(@Valid @RequestBody CrearIncidenteDTO dto) {
        String email = org.springframework.security.core.context.SecurityContextHolder
                        .getContext().getAuthentication().getName();
        
        return ResponseEntity.ok(incidenteService.crearIncidenteConEmail(dto, email));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<IncidenteDTO>> listarPorUsuario(@PathVariable Long usuarioId){

        return ResponseEntity.ok(incidenteService.listarIncidentesPorUsuario(usuarioId));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIncidente(@PathVariable Long id){

        incidenteService.eliminarIncidente(id);

        return ResponseEntity.noContent().build();

    }

    @PostMapping("/{id}/foto")
    public ResponseEntity<String> subirFoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file){

        return ResponseEntity.ok(incidenteService.subirFoto(id, file));
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<IncidenteDTO>> buscarAvanzado(
            @RequestParam Double lat, 
            @RequestParam Double lng, 
            @RequestParam Double radio,
            @RequestParam String tipo) {
        return ResponseEntity.ok(incidenteService.buscarAvanzado(lat, lng, radio, tipo));
    }

    @PostMapping("/{id}/votar")
    public ResponseEntity<Map<String, Object>> votarIncidente(@PathVariable Long id) {
        String email = org.springframework.security.core.context.SecurityContextHolder
                        .getContext().getAuthentication().getName();

        boolean votado = incidenteService.votarIncidente(id, email);
        long totalVotos = incidenteService.contarVotos(id);

        String mensaje = votado ? "Voto registrado" : "No puedes votar por tu propio reporte o ya votaste";

        return ResponseEntity.ok(Map.of(
            "votado", votado,
            "totalVotos", totalVotos,
            "mensaje", mensaje
        ));
    }

    @GetMapping("/{id}/votos")
    public ResponseEntity<Map<String, Object>> obtenerVotos(@PathVariable Long id) {
        String email = org.springframework.security.core.context.SecurityContextHolder
                        .getContext().getAuthentication().getName();

        long totalVotos = incidenteService.contarVotos(id);
        boolean usuarioHaVotado = incidenteService.usuarioHaVotado(id, email);

        return ResponseEntity.ok(Map.of(
            "totalVotos", totalVotos,
            "usuarioHaVotado", usuarioHaVotado
        ));
    }
}