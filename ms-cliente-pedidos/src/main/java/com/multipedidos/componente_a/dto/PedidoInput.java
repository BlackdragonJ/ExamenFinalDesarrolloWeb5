package com.multipedidos.componente_a.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PedidoInput {

  @NotNull
  private Long clienteId;

  @NotNull
  @Size(min = 1)
  private List<ProductoDto> productos;
}
