package com.prueba.apirest.controllers;

import com.prueba.apirest.dto.ProductoLstResponse;
import com.prueba.apirest.dto.ProductoRequest;
import com.prueba.apirest.dto.ProductoRequestId;
import com.prueba.apirest.dto.ProductoResponse;
import com.prueba.apirest.entities.Producto;
import com.prueba.apirest.services.ProductoService;
import com.prueba.apirest.utils.JwtToken;
import com.prueba.apirest.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/producto")
@Tag(name="Productos")
public class ProductoController {

    @Autowired
    private final ProductoService productoservice;

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final JwtToken jwtToken;

    public ProductoController(ProductoService productoservice, JwtUtil jwtUtil, JwtToken jwtToken) {
        this.productoservice = productoservice;
        this.jwtUtil = jwtUtil;
        this.jwtToken = jwtToken;
    }


    @PostMapping("/crear")
    @Operation(summary="Crear producto")
    public ResponseEntity<?> create(
            @RequestBody ProductoRequest request)
    {
        Map<String, Object> body = new HashMap<>();
        try{
            productoservice.save(request);
            body.put("mensaje", "Producto Creado");
            body.put("code", HttpStatus.CREATED.value());
            return ResponseEntity.status(HttpStatus.CREATED).body(body);
        }
        catch (RuntimeException e)
        {
            if (e.getMessage().contains("No autorizado")) {
                body.put("mensaje", e.getMessage());
                body.put("code", HttpStatus.UNAUTHORIZED.value());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(body);
            }

            body.put("mensaje", e.getMessage());
            body.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }
    @PostMapping("/buscar")
    @Operation(summary="Buscar producto por Id")
    public ResponseEntity<?> buscarporId(
            @RequestBody ProductoRequestId request)
    {
        Map<String, Object> body = new HashMap<>();
        try{
            Optional<Producto> producto = productoservice.getProductoId(request);
            if (!producto.isPresent()) {
                body.put("mensaje", "No existe Producto");
                body.put("code", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.OK).body(body);
            } else {
                body.put("code", HttpStatus.OK.value());
                body.put("data", producto);
                return ResponseEntity.status(HttpStatus.OK).body(body);
            }
        }
        catch (RuntimeException e)
        {
            if (e.getMessage().contains("No autorizado")) {
                body.put("mensaje", e.getMessage());
                body.put("code", HttpStatus.UNAUTHORIZED.value());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(body);
            }

            body.put("mensaje", e.getMessage());
            body.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }
    @PutMapping("/actualizar")
    @Operation(summary="Actualizar producto")
    public ResponseEntity<?> update(
            @RequestBody ProductoRequest request)
    {
        Map<String, Object> body = new HashMap<>();
        try{
            productoservice.update(request);
            body.put("mensaje", "Producto Actualizado");
            body.put("code", HttpStatus.OK.value());
            return ResponseEntity.ok(body);
        }
        catch (RuntimeException e)
        {
            if (e.getMessage().contains("No autorizado")) {
                body.put("mensaje", e.getMessage());
                body.put("code", HttpStatus.UNAUTHORIZED.value());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(body);
            }

            body.put("mensaje", e.getMessage());
            body.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }
    @GetMapping("/listar")
    @Operation(summary="Listar productos y generar token")
    public ResponseEntity<?> findAll()
    {
        String token = jwtUtil.generateToken("admin");

        Map<String, Object> body = new HashMap<>();
        try{
            List<Producto> lst = productoservice.getAll();
            ProductoLstResponse response = new ProductoLstResponse();
            jwtToken.add(token);
            response.token = token;
            response.data = lst;
            return ResponseEntity.ok(response);
        }
        catch (RuntimeException e)
        {
            body.put("mensaje", e.getMessage());
            body.put("code", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }
    }
    @DeleteMapping("/eliminar")
    @Operation(summary="Eliminar producto")
    public ResponseEntity<?> eliminar(@RequestBody ProductoRequestId request){
        Map<String, Object> body = new HashMap<>();
        try{
            body.put("mensaje", "Producto Eliminado");
            body.put("code", HttpStatus.OK.value());
            productoservice.delete(request);
            return ResponseEntity.ok(body);
        }catch (RuntimeException e)
        {
            if (e.getMessage().contains("No autorizado")) {
                body.put("mensaje", e.getMessage());
                body.put("code", HttpStatus.UNAUTHORIZED.value());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(body);
            }

            body.put("mensaje", e.getMessage());
            body.put("code", HttpStatus.NO_CONTENT.value());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(body);
        }
    }
}
