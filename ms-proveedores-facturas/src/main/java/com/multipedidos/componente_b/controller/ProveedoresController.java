package com.multipedidos.componente_b.controller;

import com.multipedidos.componente_b.dto.ProveedorDto;
import com.multipedidos.componente_b.dto.ProveedorInput;
import com.multipedidos.componente_b.entity.ProveedorEntity;
import com.multipedidos.componente_b.repository.ProveedorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/proveedores")
@RequiredArgsConstructor
public class ProveedoresController {

    private final ProveedorRepository proveedorRepository;

    @PostMapping
    public ResponseEntity<ProveedorDto> crearProveedor(@Valid @RequestBody ProveedorInput input) {
        ProveedorEntity entity = ProveedorEntity.builder()
                .nombre(input.getNombre())
                .correo(input.getCorreo())
                .build();

        ProveedorEntity saved = proveedorRepository.save(entity);

        ProveedorDto dto = ProveedorDto.builder()
                .id(saved.getId())
                .nombre(saved.getNombre())
                .correo(saved.getCorreo())
                .build();

        return ResponseEntity
                .created(URI.create("/proveedores/" + saved.getId()))
                .body(dto);
    }

    @GetMapping
    public List<ProveedorDto> listarProveedores() {
        return proveedorRepository.findAll()
                .stream()
                .map(p -> ProveedorDto.builder()
                        .id(p.getId())
                        .nombre(p.getNombre())
                        .correo(p.getCorreo())
                        .build())
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDto> obtenerProveedor(@PathVariable Long id) {
        return proveedorRepository.findById(id)
                .map(p -> ProveedorDto.builder()
                        .id(p.getId())
                        .nombre(p.getNombre())
                        .correo(p.getCorreo())
                        .build())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
