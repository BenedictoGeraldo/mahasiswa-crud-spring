package com.bene.mahasiswacrudspring.model;

import jakarta.persistence.*;
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

    @Column(nullable = false, length = 100)
    private String nama;

    @Column(nullable = false, unique = true, length = 20)
    private String nim;

    @Column(name = "PROGRAM_STUDI", length = 50)
    private String programStudi;

    @Column(length = 4)
    private Integer angkatan;
}
