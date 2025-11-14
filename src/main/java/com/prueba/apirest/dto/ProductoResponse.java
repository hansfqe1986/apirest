package com.prueba.apirest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductoResponse {

    private Long id;
    private String nombreproducto;
    private String descripcion;
    private Double precio;
    private Integer stock;
    @JsonProperty("fechacreacion")
    private LocalDateTime fechacreacion;
    @JsonProperty("fechamodificacion")
    private LocalDateTime fechamodificacion;

}
