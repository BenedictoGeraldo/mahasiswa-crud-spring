package com.bene.mahasiswacrudspring.service.impl;

import com.bene.mahasiswacrudspring.model.Mahasiswa;
import com.bene.mahasiswacrudspring.repository.MahasiswaRepository;
import com.bene.mahasiswacrudspring.service.MahasiswaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MahasiswaServiceImpl implements MahasiswaService {

    private final MahasiswaRepository repository;

    public MahasiswaServiceImpl(MahasiswaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mahasiswa create(Mahasiswa mahasiswa) {
        return repository.save(mahasiswa);
    }

    @Override
    public List<Mahasiswa> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Mahasiswa> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Mahasiswa update(Long id, Mahasiswa newData) {
        Mahasiswa existing = repository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                "Mahasiswa dengan ID " + id + " tidak ditemukan"));

        existing.setNama(newData.getNama());
        existing.setNim(newData.getNim());
        existing.setProgramStudi(newData.getProgramStudi());
        existing.setAngkatan(newData.getAngkatan());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
