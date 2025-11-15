package com.multipedidos.componente_b.controller;

import com.multipedidos.componente_b.dto.FacturaDto;
import com.multipedidos.componente_b.dto.FacturaInput;
import com.multipedidos.componente_b.service.FacturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/facturas")
@RequiredArgsConstructor
public class FacturasController {

    private final FacturaService facturaService;

    @PostMapping
    public ResponseEntity<FacturaDto> crearFactura(@Valid @RequestBody FacturaInput input) {
        FacturaDto dto = facturaService.crearFactura(input);
        return ResponseEntity
                .created(URI.create("/facturas/" + dto.getId()))
                .body(dto);
    }

    @GetMapping
    public List<FacturaDto> listarFacturas() {
        return facturaService.listarFacturas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaDto> obtenerFactura(@PathVariable Long id) {
        try {
            FacturaDto dto = facturaService.obtenerFactura(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
