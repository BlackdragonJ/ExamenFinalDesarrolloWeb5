package com.multipedidos.componente_a.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PedidoDto {

  private Long id;
  private Long clienteId;
  private List<ProductoDto> productos;
  private Double total;
}
