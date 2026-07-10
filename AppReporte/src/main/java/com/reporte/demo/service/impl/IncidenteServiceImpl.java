package com.reporte.demo.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.reporte.demo.dto.CrearIncidenteDTO;
import com.reporte.demo.dto.IncidenteDTO;
import com.reporte.demo.entity.Incidente;
import com.reporte.demo.entity.TipoIncidente;
import com.reporte.demo.entity.Usuario;
import com.reporte.demo.entity.VotoIncidente;
import com.reporte.demo.mapper.IncidenteMapper;
import com.reporte.demo.repository.IncidenteRepository;
import com.reporte.demo.repository.UsuarioRepository;
import com.reporte.demo.repository.VotoIncidenteRepository;
import com.reporte.demo.service.IncidenteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncidenteServiceImpl implements IncidenteService {

    private final IncidenteRepository incidenteRepository;
    private final UsuarioRepository usuarioRepository;
    private final VotoIncidenteRepository votoIncidenteRepository;
    private final IncidenteMapper incidenteMapper;

    @Override
    public IncidenteDTO crearIncidenteConEmail(CrearIncidenteDTO dto, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Incidente incidente = new Incidente();
        incidente.setTitulo(dto.getTitulo());
        incidente.setDescripcion(dto.getDescripcion());
        incidente.setLatitud(dto.getLatitud());
        incidente.setLongitud(dto.getLongitud());
        incidente.setTipoIncidente(TipoIncidente.valueOf(dto.getTipo()));
        incidente.setFecha(LocalDateTime.now());
        incidente.setUsuario(usuario);

        Incidente guardado = incidenteRepository.save(incidente);
        return incidenteMapper.toDTO(guardado);
    }
    
    @Override
    public List<IncidenteDTO> listarIncidentes() {

        return incidenteRepository.findAll()
                .stream()
                .map(incidenteMapper::toDTO)
                .toList();
    }

    @Override
    public List<IncidenteDTO> listarIncidentesPorTipo(TipoIncidente tipo) {

        return incidenteRepository.findByTipoIncidenteOrderByFechaDesc(tipo)
                .stream()
                .map(incidenteMapper::toDTO)
                .toList();
    }

    @Override
    public List<IncidenteDTO> listarIncidentesPorUsuario(Long usuarioId) {

        return incidenteRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(incidenteMapper::toDTO)
                .toList();
    }

    @Override
    public IncidenteDTO obtenerIncidente(Long id) {

        Incidente incidente = incidenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado"));

        return incidenteMapper.toDTO(incidente);
    }

    @Override
    public void eliminarIncidente(Long id) {

        incidenteRepository.deleteById(id);

    }
    
    @Override
    public String subirFoto(Long incidenteId, MultipartFile file) {

        try {

            Incidente incidente = incidenteRepository.findById(incidenteId)
                    .orElseThrow(() -> new RuntimeException("Incidente no encontrado"));

            String nombreArchivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path ruta = Paths.get("uploads");

            if(!Files.exists(ruta)){
                Files.createDirectories(ruta);
            }

            Files.copy(file.getInputStream(), ruta.resolve(nombreArchivo));

            incidente.setFotoUrl(nombreArchivo);

            incidenteRepository.save(incidente);

            return nombreArchivo;

        } catch (Exception e){
            throw new RuntimeException("Error al subir la imagen");
        }
    }

    @Override
    public List<IncidenteDTO> buscarAvanzado(Double lat, Double lng, Double radio, String tipo) {
        return incidenteRepository.buscarAvanzado(lat, lng, radio, tipo)
                .stream()
                .map(incidenteMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public boolean votarIncidente(Long incidenteId, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Incidente incidente = incidenteRepository.findById(incidenteId)
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado"));

        if (incidente.getUsuario().getId().equals(usuario.getId())) {
            return false;
        }

        if (votoIncidenteRepository.existsByUsuarioIdAndIncidenteId(usuario.getId(), incidenteId)) {
            return false;
        }

        VotoIncidente voto = new VotoIncidente();
        voto.setUsuario(usuario);
        voto.setIncidente(incidente);
        voto.setFechaVoto(LocalDateTime.now());
        votoIncidenteRepository.save(voto);

        incidente.setCantidadVotos(incidente.getCantidadVotos() + 1);
        incidenteRepository.save(incidente);

        return true;
    }

    @Override
    public long contarVotos(Long incidenteId) {
        return votoIncidenteRepository.countByIncidenteId(incidenteId);
    }

    @Override
    public boolean usuarioHaVotado(Long incidenteId, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return votoIncidenteRepository.existsByUsuarioIdAndIncidenteId(usuario.getId(), incidenteId);
    }
}
