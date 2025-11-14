package com.prueba.apirest.dto.mapper;

import com.prueba.apirest.dto.ProductoResponse;
import com.prueba.apirest.entities.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoResponse productoResponse(Producto p)
    {
          ProductoResponse response = new ProductoResponse();
          response.setId(p.getId());
          response.setNombreproducto(p.getNombreproducto());
          response.setPrecio(p.getPrecio());
          response.setFechacreacion(p.getFechacreacion());
          response.setFechamodificacion(p.getFechamodificacion());
          return response;
    }
}
