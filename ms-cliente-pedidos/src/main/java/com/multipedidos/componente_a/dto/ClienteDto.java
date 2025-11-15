package com.multipedidos.componente_a.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ClienteDto {
  private Long id;
  private String nombre;
  private String correo;
}
