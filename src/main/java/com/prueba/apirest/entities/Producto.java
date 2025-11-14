package com.prueba.apirest.entities;

import javax.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name ="producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombreproducto", length = 255, nullable = false)
    private String nombreproducto;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "precio",nullable = false)
    private Double precio;

    @Column(name = "stock", length = 255, nullable = false)
    private Integer stock;

    @Column(name = "fechacreacion", nullable = false,updatable = false)
    private LocalDateTime fechacreacion = LocalDateTime.now();

    @Column(name = "fechamodificacion", nullable = false)
    private LocalDateTime fechamodificacion = LocalDateTime.now();

    @PreUpdate
    public void onUpdate()
    {
        this.fechamodificacion = LocalDateTime.now();
    }
}
