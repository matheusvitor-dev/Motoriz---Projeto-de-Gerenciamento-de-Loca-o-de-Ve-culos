package com.motoriz.Controllers;

import com.motoriz.Dtos.VeiculoRequestDTO;
import com.motoriz.Dtos.VeiculoResponseDTO;
import com.motoriz.Enums.VeiculoStatus;
import com.motoriz.Services.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VeiculoController {

    private final VeiculoService veiculoService;

    @GetMapping
    public ResponseEntity<List<VeiculoResponseDTO>> findAll() {
        return ResponseEntity.ok(veiculoService.findAll());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<VeiculoResponseDTO>> findByStatus(@PathVariable VeiculoStatus status) {
        return ResponseEntity.ok(veiculoService.findByStatus(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(veiculoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<VeiculoResponseDTO> create(@RequestBody VeiculoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> update(@PathVariable UUID id, @RequestBody VeiculoRequestDTO dto) {
        return ResponseEntity.ok(veiculoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        veiculoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
