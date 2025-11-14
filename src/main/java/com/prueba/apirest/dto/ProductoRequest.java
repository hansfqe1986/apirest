package com.prueba.apirest.dto;

import lombok.Data;

@Data
public class ProductoRequest {
    private Long id;
    private String nombreproducto;
    private String descripcion;
    private Double precio;
    private Integer stock;
}
