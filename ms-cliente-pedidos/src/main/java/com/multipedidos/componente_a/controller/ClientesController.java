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

import com.multipedidos.componente_a.dto.ClienteDto;
import com.multipedidos.componente_a.dto.ClienteInput;
import com.multipedidos.componente_a.entity.ClienteEntity;
import com.multipedidos.componente_a.repository.ClienteRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientesController {

  private final ClienteRepository clienteRepository;

  @PostMapping
  public ResponseEntity<ClienteDto> crearCliente(@Valid @RequestBody ClienteInput input) {
    ClienteEntity entity = ClienteEntity.builder()
      .nombre(input.getNombre())
      .correo(input.getCorreo())
      .build();

    ClienteEntity saved = clienteRepository.save(entity);

    ClienteDto dto = ClienteDto.builder()
      .id(saved.getId())
      .nombre(saved.getNombre())
      .correo(saved.getCorreo())
      .build();

    return ResponseEntity
      .created(URI.create("/clientes/" + saved.getId()))
      .body(dto);
  }

  @GetMapping
  public List<ClienteDto> listarClientes() {
    return clienteRepository.findAll()
      .stream()
      .map(c -> ClienteDto.builder()
        .id(c.getId())
        .nombre(c.getNombre())
        .correo(c.getCorreo())
        .build())
      .toList();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClienteDto> obtenerCliente(@PathVariable Long id) {
    return clienteRepository.findById(id)
      .map(c -> ClienteDto.builder()
        .id(c.getId())
        .nombre(c.getNombre())
        .correo(c.getCorreo())
        .build())
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }
}