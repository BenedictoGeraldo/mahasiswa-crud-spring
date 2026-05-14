package com.bene.mahasiswacrudspring.service;

import com.bene.mahasiswacrudspring.model.Mahasiswa;

import java.util.List;
import java.util.Optional;

public interface MahasiswaService {

    Mahasiswa create(Mahasiswa mahasiswa);

    List<Mahasiswa> getAll();

    Optional<Mahasiswa> getById(Long id);

    Mahasiswa update(Long id, Mahasiswa mahasiswa);

    void delete(Long id);
}
