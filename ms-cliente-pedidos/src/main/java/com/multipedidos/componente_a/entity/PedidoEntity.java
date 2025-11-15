package com.multipedidos.componente_a.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pedidos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PedidoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "cliente_id", nullable = false)
  private ClienteEntity cliente;

  @ElementCollection
  @CollectionTable(name = "pedido_productos", joinColumns = @JoinColumn(name = "pedido_id"))
  @Builder.Default
  private List<ProductoEmbeddable> productos = new ArrayList<>();

  @Column(nullable = false)
  private Double total;
}
