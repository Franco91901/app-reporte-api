package com.reporte.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "votos_incidente", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"usuario_id", "incidente_id"})
})
@Data
@NoArgsConstructor
public class VotoIncidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "incidente_id", nullable = false)
    private Incidente incidente;

    private LocalDateTime fechaVoto;
}
