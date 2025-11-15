package com.multipedidos.componente_b.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multipedidos.componente_b.entity.FacturaEntity;

public interface FacturaRepository extends JpaRepository<FacturaEntity, Long> {
    
}
