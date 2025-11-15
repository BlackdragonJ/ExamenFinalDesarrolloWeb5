package com.multipedidos.componente_b.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.multipedidos.componente_b.dto.FacturaDto;
import com.multipedidos.componente_b.dto.FacturaInput;
import com.multipedidos.componente_b.dto.PedidoReferenciaDto;
import com.multipedidos.componente_b.entity.FacturaEntity;
import com.multipedidos.componente_b.entity.PedidoReferenciaEmbeddable;
import com.multipedidos.componente_b.entity.ProveedorEntity;
import com.multipedidos.componente_b.repository.FacturaRepository;
import com.multipedidos.componente_b.repository.ProveedorRepository;
import com.multipedidos.negocio.OperacionesNegocio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final ProveedorRepository proveedorRepository;

    public FacturaDto crearFactura(FacturaInput input) {
        ProveedorEntity proveedor = proveedorRepository.findById(input.getProveedorId())
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));

        List<PedidoReferenciaEmbeddable> pedidosEmb = input.getPedidos()
                .stream()
                .map(p -> PedidoReferenciaEmbeddable.builder()
                        .pedidoId(p.getPedidoId())
                        .total(p.getTotal())
                        .build())
                .toList();

        double subtotal = input.getPedidos().stream()
                .mapToDouble(PedidoReferenciaDto::getTotal)
                .sum();

        double totalConIVA = OperacionesNegocio.calcularTotalConIVA(subtotal);
        double totalFinal = OperacionesNegocio.aplicarDescuento(totalConIVA, 0.0); // sin descuento por ahora

        FacturaEntity entity = FacturaEntity.builder()
                .proveedor(proveedor)
                .pedidos(pedidosEmb)
                .totalFactura(totalFinal)
                .build();

        FacturaEntity saved = facturaRepository.save(entity);

        return mapToDto(saved);
    }

    public List<FacturaDto> listarFacturas() {
        return facturaRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public FacturaDto obtenerFactura(Long id) {
        FacturaEntity entity = facturaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));
        return mapToDto(entity);
    }

    private FacturaDto mapToDto(FacturaEntity entity) {
        List<PedidoReferenciaDto> pedidos = entity.getPedidos()
                .stream()
                .map(p -> PedidoReferenciaDto.builder()
                        .pedidoId(p.getPedidoId())
                        .total(p.getTotal())
                        .build())
                .toList();

        return FacturaDto.builder()
                .id(entity.getId())
                .proveedorId(entity.getProveedor().getId())
                .pedidos(pedidos)
                .totalFactura(entity.getTotalFactura())
                .build();
    }
}
