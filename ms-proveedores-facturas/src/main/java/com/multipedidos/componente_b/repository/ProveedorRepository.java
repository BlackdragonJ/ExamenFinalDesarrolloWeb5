package com.multipedidos.componente_b.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multipedidos.componente_b.entity.ProveedorEntity;

public interface ProveedorRepository extends JpaRepository<ProveedorEntity, Long> {
    
}
