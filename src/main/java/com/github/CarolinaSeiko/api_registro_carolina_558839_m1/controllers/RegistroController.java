package com.github.CarolinaSeiko.api_registro_carolina_558839_m1.controllers;

import com.github.CarolinaSeiko.api_registro_carolina_558839_m1.dto.RegistroRequestDTO;
import com.github.CarolinaSeiko.api_registro_carolina_558839_m1.dto.RegistroResponseDTO;
import com.github.CarolinaSeiko.api_registro_carolina_558839_m1.services.RegistroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/registros")
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    @GetMapping
    public ResponseEntity<List<RegistroResponseDTO>> findAll() {
        return ResponseEntity.ok(registroService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(registroService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RegistroResponseDTO> create(
            @RequestBody @Valid RegistroRequestDTO requestDTO) {

        RegistroResponseDTO responseDTO = registroService.save(requestDTO);
        URI uri = URI.create("/registros/" + responseDTO.getRegistroId());
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid RegistroRequestDTO requestDTO) {

        return ResponseEntity.ok(registroService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        registroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
