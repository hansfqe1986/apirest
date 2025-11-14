package com.prueba.apirest.services;

import com.prueba.apirest.dto.ProductoLstResponse;
import com.prueba.apirest.dto.ProductoRequest;
import com.prueba.apirest.dto.ProductoRequestId;
import com.prueba.apirest.dto.ProductoResponse;
import com.prueba.apirest.dto.mapper.ProductoMapper;
import com.prueba.apirest.entities.Producto;
import com.prueba.apirest.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Transactional
    public Producto save(
            @RequestBody ProductoRequest request) {

        Producto prod = new Producto();
        prod.setNombreproducto(request.getNombreproducto());
        prod.setDescripcion(request.getDescripcion());
        prod.setPrecio(request.getPrecio());
        prod.setStock(request.getStock());

        return productoRepository.save(prod);
    }

    @Transactional
    public Producto update(ProductoRequest request) {

        Producto findProducto = productoRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        findProducto.setId(request.getId());
        findProducto.setNombreproducto(request.getNombreproducto());
        findProducto.setDescripcion(request.getDescripcion());
        findProducto.setPrecio(request.getPrecio());
        findProducto.setStock(request.getStock());
        findProducto.onUpdate();
        return productoRepository.save(findProducto);
    }

    @Transactional
    public void delete(ProductoRequestId request) {
        Long id = request.getId();
        productoRepository.deleteById(id);
    }

    @Transactional
    public List<Producto> getAll() {
        return productoRepository.findAll();
    }
    @Transactional
    public Optional<Producto> getProductoId(ProductoRequestId request) {
        Long id = request.getId();
        return productoRepository.findById(id);
    }
}
