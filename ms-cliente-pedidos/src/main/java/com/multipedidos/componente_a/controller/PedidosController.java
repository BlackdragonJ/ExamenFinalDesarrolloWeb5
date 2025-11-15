package com.multipedidos.componente_a.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multipedidos.componente_a.dto.PedidoDto;
import com.multipedidos.componente_a.dto.PedidoInput;
import com.multipedidos.componente_a.service.PedidoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidosController {

  private final PedidoService pedidoService;

  @PostMapping
  public ResponseEntity<PedidoDto> crearPedido(@Valid @RequestBody PedidoInput input) {
    PedidoDto dto = pedidoService.crearPedido(input);
    return ResponseEntity
      .created(URI.create("/pedidos/" + dto.getId()))
      .body(dto);
  }

  @GetMapping
  public List<PedidoDto> listarPedidos() {
    return pedidoService.listarPedidos();
  }

  @GetMapping("/{id}")
  public ResponseEntity<PedidoDto> obtenerPedido(@PathVariable Long id) {
    try {
      PedidoDto dto = pedidoService.obtenerPedido(id);
      return ResponseEntity.ok(dto);
    } catch (IllegalArgumentException ex) {
      return ResponseEntity.notFound().build();
    }
  }
}
