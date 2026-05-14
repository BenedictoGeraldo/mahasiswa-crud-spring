package com.bene.mahasiswacrudspring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MAHASISWA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mahasiswa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mahasiswa_seq")
    @SequenceGenerator(name = "mahasiswa_seq", sequenceName = "MAHASISWA_SEQ", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Nama wajib diisi")
    @Size(max = 100, message = "Nama maksimal 100 karakter")
    @Column(nullable = false, length = 100)
    private String nama;

    @NotBlank(message = "NIM wajib diisi")
    @Size(max = 20, message = "NIM maksimal 20 karakter")
    @Column(nullable = false, unique = true, length = 20)
    private String nim;

    @Size(max = 50, message = "Program studi maksimal 50 karakter")
    @Column(name = "PROGRAM_STUDI", length = 50)
    private String programStudi;

    @Min(value = 2000, message = "Angkatan minimal 2000")
    @Max(value = 2100, message = "Angkatan maksimal 2100")
    private Integer angkatan;
}
