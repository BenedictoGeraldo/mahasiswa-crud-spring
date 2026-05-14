package com.bene.mahasiswacrudspring.repository;

import com.bene.mahasiswacrudspring.model.Mahasiswa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Long> {

    Optional<Mahasiswa> findByNim(String nim);

    List<Mahasiswa> findByProgramStudi(String programStudi);

    List<Mahasiswa> findByAngkatan(Integer angkatan);

    List<Mahasiswa> findByNamaContainingIgnoreCase(String nama);
}
