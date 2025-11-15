package com.multipedidos.componente_a.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multipedidos.componente_a.entity.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

}
