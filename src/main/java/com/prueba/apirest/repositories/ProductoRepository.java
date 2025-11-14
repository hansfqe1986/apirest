package com.prueba.apirest.repositories;

import com.prueba.apirest.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Long>{

}
