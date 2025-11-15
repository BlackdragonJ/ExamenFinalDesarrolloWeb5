package com.multipedidos.componente_b.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class FacturaDto {

    private Long id;
    private Long proveedorId;
    private List<PedidoReferenciaDto> pedidos;
    private Double totalFactura;
}
