package com.ads.web.reclamos.entities;
import java.sql.Date;
import java.time.LocalDateTime;

import com.ads.web.usuario.entities.User;
import com.ads.web.ventas.entities.Venta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "reclamos")
@Data
@RequiredArgsConstructor
public class Reclamo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fecha;
    private String descripcion;
    private String estado;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;
    

  /*   @Enumerated(EnumType.STRING)
    private EstadoReclamo estado;
*/}

