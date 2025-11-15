package com.multipedidos.componente_a.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.multipedidos.componente_a.dto.PedidoDto;
import com.multipedidos.componente_a.dto.PedidoInput;
import com.multipedidos.componente_a.dto.ProductoDto;
import com.multipedidos.componente_a.entity.ClienteEntity;
import com.multipedidos.componente_a.entity.PedidoEntity;
import com.multipedidos.componente_a.entity.ProductoEmbeddable;
import com.multipedidos.componente_a.repository.ClienteRepository;
import com.multipedidos.componente_a.repository.PedidoRepository;
import com.multipedidos.negocio.OperacionesNegocio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

  private final PedidoRepository pedidoRepository;
  private final ClienteRepository clienteRepository;

  public PedidoDto crearPedido(PedidoInput input) {
    ClienteEntity cliente = clienteRepository.findById(input.getClienteId())
      .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

    // convertir productos DTO a embebidos
    List<ProductoEmbeddable> productosEmb = input.getProductos()
      .stream()
      .map(p -> ProductoEmbeddable.builder()
        .nombre(p.getNombre())
        .precio(p.getPrecio())
        .build())
      .toList();

    // calcular subtotal
    double subtotal = input.getProductos().stream()
      .mapToDouble(ProductoDto::getPrecio)
      .sum();

    // usar libreria -> IVA + (por ahora sin descuento pero luego lo agregamos) 
    double totalConIVA = OperacionesNegocio.calcularTotalConIVA(subtotal);
    double totalFinal = OperacionesNegocio.aplicarDescuento(totalConIVA, 0.0);

    PedidoEntity entity = PedidoEntity.builder()
      .cliente(cliente)
      .productos(productosEmb)
      .total(totalFinal)
      .build();

    PedidoEntity saved = pedidoRepository.save(entity);

    return mapToDto(saved);
  }

  public List<PedidoDto> listarPedidos() {
    return pedidoRepository.findAll()
      .stream()
      .map(this::mapToDto)
      .toList();
  }

  public PedidoDto obtenerPedido(Long id) {
    PedidoEntity entity = pedidoRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
    return mapToDto(entity);
  }

  private PedidoDto mapToDto(PedidoEntity entity) {
    List<ProductoDto> productos = entity.getProductos()
      .stream()
      .map(p -> ProductoDto.builder()
        .nombre(p.getNombre())
        .precio(p.getPrecio())
        .build())
      .toList();

    return PedidoDto.builder()
      .id(entity.getId())
      .clienteId(entity.getCliente().getId())
      .productos(productos)
      .total(entity.getTotal())
      .build();
  }
}
