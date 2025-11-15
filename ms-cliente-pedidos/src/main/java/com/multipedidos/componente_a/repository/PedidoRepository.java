package com.multipedidos.componente_a.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multipedidos.componente_a.entity.PedidoEntity;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

}
