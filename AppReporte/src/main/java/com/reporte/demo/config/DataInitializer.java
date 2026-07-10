package com.reporte.demo.config;

import com.reporte.demo.entity.Incidente;
import com.reporte.demo.entity.Rol;
import com.reporte.demo.entity.TipoIncidente;
import com.reporte.demo.entity.Usuario;
import com.reporte.demo.entity.VotoIncidente;
import com.reporte.demo.repository.IncidenteRepository;
import com.reporte.demo.repository.UsuarioRepository;
import com.reporte.demo.repository.VotoIncidenteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final IncidenteRepository incidenteRepository;
    private final VotoIncidenteRepository votoIncidenteRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository,
                           IncidenteRepository incidenteRepository,
                           VotoIncidenteRepository votoIncidenteRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.incidenteRepository = incidenteRepository;
        this.votoIncidenteRepository = votoIncidenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            System.out.println("Ya existen datos en la base de datos. No se insertan datos de prueba.");
            return;
        }

        System.out.println("Insertando datos de prueba...");

        // USUARIOS
        Usuario juan = crearUsuario("Juan", "Perez", "juan@test.com");
        Usuario maria = crearUsuario("Maria", "Lopez", "maria@test.com");
        Usuario carlos = crearUsuario("Carlos", "Garcia", "carlos@test.com");
        Usuario ana = crearUsuario("Ana", "Torres", "ana@test.com");
        Usuario luis = crearUsuario("Luis", "Ramirez", "luis@test.com");

        // INCIDENTES
        Incidente inc1 = crearIncidente(
            "Semáforo dañado en Av. Principal",
            "El semáforo de la intersección principal está intermitente",
            -12.0463, -77.0427, TipoIncidente.INFRAESTRUCTURA, juan, 2
        );

        Incidente inc2 = crearIncidente(
            "Accidente en Cruce Real",
            "Choque entre dos vehículos bloquea un carril",
            -12.0500, -77.0350, TipoIncidente.TRANSITO, maria, 1
        );

        Incidente inc3 = crearIncidente(
            "Basura acumulada en Parque Central",
            "No se ha recolectado la basura en 3 días",
            -12.0420, -77.0300, TipoIncidente.LIMPIEZA, carlos, 2
        );

        Incidente inc4 = crearIncidente(
            "Bache grande en Calle Los Olivos",
            "Hoyado de más de 50cm que daña vehículos",
            -12.0550, -77.0480, TipoIncidente.INFRAESTRUCTURA, ana, 0
        );

        Incidente inc5 = crearIncidente(
            "Tráfico pesado en Av. Universidad",
            "Demasiados vehículos, falta señalización",
            -12.0380, -77.0520, TipoIncidente.TRANSITO, luis, 1
        );

        Incidente inc6 = crearIncidente(
            "Pozas de agua en Jr. Comercio",
            "Acumulación de agua por drenaje tapado",
            -12.0490, -77.0370, TipoIncidente.LIMPIEZA, juan, 0
        );

        Incidente inc7 = crearIncidente(
            "Graffiti en Muro Municipal",
            "Vandalismo en la fachada del edificio municipal",
            -12.0410, -77.0450, TipoIncidente.INFRAESTRUCTURA, maria, 2
        );

        Incidente inc8 = crearIncidente(
            "Calle inundada por lluvia",
            "El agua no baja por el desagüe obstruido",
            -12.0530, -77.0290, TipoIncidente.LIMPIEZA, carlos, 1
        );

        // VOTOS (respetando max 2 por incidente)
        crearVoto(inc1, ana);
        crearVoto(inc1, luis);

        crearVoto(inc2, carlos);

        crearVoto(inc3, juan);
        crearVoto(inc3, ana);

        crearVoto(inc5, ana);

        crearVoto(inc7, carlos);
        crearVoto(inc7, luis);

        crearVoto(inc8, maria);

        System.out.println("Datos de prueba insertados correctamente.");
        System.out.println("Usuarios creados: juan@test.com, maria@test.com, carlos@test.com, ana@test.com, luis@test.com");
        System.out.println("Contraseña para todos: prueba123");
    }

    private Usuario crearUsuario(String nombres, String apellidos, String email) {
        Usuario usuario = new Usuario();
        usuario.setNombres(nombres);
        usuario.setApellidos(apellidos);
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode("prueba123"));
        usuario.setRol(Rol.ROLE_USER);
        usuario.setFechaRegistro(LocalDateTime.now().minusDays((long) (Math.random() * 10)));
        return usuarioRepository.save(usuario);
    }

    private Incidente crearIncidente(String titulo, String descripcion,
                                      Double latitud, Double longitud,
                                      TipoIncidente tipo, Usuario usuario,
                                      int votos) {
        Incidente incidente = new Incidente();
        incidente.setTitulo(titulo);
        incidente.setDescripcion(descripcion);
        incidente.setLatitud(latitud);
        incidente.setLongitud(longitud);
        incidente.setTipoIncidente(tipo);
        incidente.setUsuario(usuario);
        incidente.setFecha(LocalDateTime.now().minusDays((long) (Math.random() * 7)));
        incidente.setFotoUrl(null);
        incidente.setCantidadVotos(votos);
        return incidenteRepository.save(incidente);
    }

    private void crearVoto(Incidente incidente, Usuario usuario) {
        VotoIncidente voto = new VotoIncidente();
        voto.setIncidente(incidente);
        voto.setUsuario(usuario);
        voto.setFechaVoto(LocalDateTime.now());
        votoIncidenteRepository.save(voto);
    }
}
