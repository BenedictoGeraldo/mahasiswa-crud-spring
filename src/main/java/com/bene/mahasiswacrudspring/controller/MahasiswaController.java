package com.bene.mahasiswacrudspring.controller;

import com.bene.mahasiswacrudspring.model.Mahasiswa;
import com.bene.mahasiswacrudspring.service.MahasiswaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mahasiswa")
public class MahasiswaController {

    private final MahasiswaService service;

    public MahasiswaController(MahasiswaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Mahasiswa> create(@Valid @RequestBody Mahasiswa mahasiswa) {
        Mahasiswa saved = service.create(mahasiswa);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Mahasiswa>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mahasiswa> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Mahasiswa> update(@PathVariable Long id,
                                             @Valid @RequestBody Mahasiswa mahasiswa) {
        return ResponseEntity.ok(service.update(id, mahasiswa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
