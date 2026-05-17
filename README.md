# Mahasiswa CRUD Spring

## Deskripsi Proyek

Mahasiswa CRUD Spring adalah REST API backend untuk manajemen data mahasiswa yang dibangun menggunakan Spring Boot 3. Aplikasi ini dirancang dengan arsitektur berlapis (layered architecture) yang memisahkan tanggung jawab antara Controller, Service, Repository, dan Model. Setiap lapisan memiliki peran spesifik: Controller menangani permintaan HTTP dan mengembalikan respons JSON, Service mengelola logika bisnis dan transaksi database, Repository menyediakan akses data melalui Spring Data JPA, serta Model merepresentasikan tabel-tabel dalam database Oracle. Sistem autentikasi menggunakan JSON Web Token (JWT) yang memungkinkan akses stateless tanpa session server, di mana setiap permintaan diverifikasi melalui token yang disertakan dalam header Authorization. Password pengguna dienkripsi menggunakan algoritma BCrypt sebelum disimpan ke database, memastikan keamanan kredensial meskipun terjadi kebocoran data. Seluruh endpoint mahasiswa dilindungi oleh Spring Security, sementara endpoint registrasi dan login terbuka untuk publik. Aplikasi ini juga dilengkapi dengan validasi input di sisi server menggunakan Bean Validation (Jakarta Validation), global exception handler untuk respons error yang konsisten dan informatif, serta konfigurasi CORS yang memungkinkan integrasi dengan frontend yang berjalan di domain atau port berbeda.

## Repositori Frontend (Vue.Js)
https://github.com/BenedictoGeraldo/mahasiswa-crud-vue.git

---

## Fitur

- **Autentikasi JWT** — Registrasi akun baru, login untuk mendapatkan token, dan perlindungan endpoint dengan JWT
- **Manajemen Mahasiswa** — Operasi CRUD lengkap (Create, Read, Update, Delete) untuk data mahasiswa mencakup nama, NIM, program studi, dan angkatan
- **Validasi Input** — Validasi di sisi server menggunakan Jakarta Bean Validation dengan pesan error berbahasa Indonesia yang jelas per field
- **Global Exception Handler** — Penanganan error terpusat yang mengembalikan respons JSON terstruktur untuk validasi gagal, error bisnis, dan error server
- **Keamanan BCrypt** — Password di-hash menggunakan algoritma BCrypt sebelum disimpan, tidak pernah disimpan dalam bentuk plain text
- **CORS** — Konfigurasi Cross-Origin Resource Sharing siap untuk integrasi dengan frontend React, Vue, atau framework lainnya
- **Hibernate DDL Auto** — Tabel dan sequence database dibuat dan diperbarui secara otomatis berdasarkan entity JPA
- **Custom Query Methods** — Pencarian mahasiswa berdasarkan NIM, program studi, angkatan, dan pencarian nama (case-insensitive)
- **Stateless Session** — Arsitektur tanpa session server, setiap permintaan bersifat independen dengan verifikasi token

## Instalasi dan Panduan Menjalankan

### Prasyarat

- JDK 17 atau lebih tinggi
- Maven 3.9+ (atau gunakan Maven Wrapper yang sudah disertakan)
- Oracle Database (versi 21c disarankan, minimal 11g)

### Konfigurasi Database

Buat user database Oracle untuk aplikasi:

```sql
CREATE USER mahasiswa_app IDENTIFIED BY password123;
GRANT CONNECT, RESOURCE, UNLIMITED TABLESPACE TO mahasiswa_app;
```

### Setup Proyek

1. Clone repositori:

```bash
git clone <url-repositori>
cd mahasiswa-crud-spring
```

2. Konfigurasi koneksi database di `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/XEPDB1
spring.datasource.username=mahasiswa_app
spring.datasource.password=password123
```

> Sesuaikan `url`, `username`, dan `password` dengan konfigurasi Oracle kamu. Format URL: `jdbc:oracle:thin:@//host:port/service_name`.

3. Jalankan aplikasi:

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux / Mac
./mvnw spring-boot:run
```

Aplikasi akan berjalan di `http://localhost:8080`. Tabel `APP_USER` dan `MAHASISWA` akan dibuat otomatis saat pertama kali startup.

### Testing Endpoint

**Registrasi akun baru:**

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"budi","password":"rahasia123"}'
```

**Login untuk mendapatkan token:**

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"budi","password":"rahasia123"}'
```

**Akses endpoint mahasiswa (wajib token):**

```bash
curl -X GET http://localhost:8080/api/mahasiswa \
  -H "Authorization: Bearer <token-dari-login>"
```

**Tambah mahasiswa baru:**

```bash
curl -X POST http://localhost:8080/api/mahasiswa \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token-dari-login>" \
  -d '{"nama":"Budi Santoso","nim":"20240001","programStudi":"Teknik Informatika","angkatan":2024}'
```

**Cari mahasiswa berdasarkan nama:**

```bash
curl -X GET "http://localhost:8080/api/mahasiswa/search?nama=budi" \
  -H "Authorization: Bearer <token-dari-login>"
```

## Struktur Proyek

```
src/main/java/com/bene/mahasiswacrudspring/
├── MahasiswaCrudSpringApplication.java     # Main class — entry point aplikasi
│
├── config/
│   ├── SecurityConfig.java                 # Konfigurasi Spring Security, CORS, filter chain
│   └── JwtAuthFilter.java                  # Filter JWT — memeriksa token di setiap request
│
├── controller/
│   ├── AuthController.java                 # Endpoint /api/auth/register dan /api/auth/login
│   └── MahasiswaController.java            # Endpoint CRUD /api/mahasiswa
│
├── dto/
│   ├── LoginRequest.java                   # Body request untuk login
│   ├── RegisterRequest.java                # Body request untuk registrasi
│   ├── AuthResponse.java                   # Respons berisi token dan username
│   └── ErrorResponse.java                  # Struktur respons error yang konsisten
│
├── exception/
│   └── GlobalExceptionHandler.java         # Penanganan exception terpusat (400, 500)
│
├── model/
│   ├── User.java                           # Entity JPA untuk tabel APP_USER
│   └── Mahasiswa.java                      # Entity JPA untuk tabel MAHASISWA
│
├── repository/
│   ├── UserRepository.java                 # JPA Repository untuk User (findByUsername)
│   └── MahasiswaRepository.java            # JPA Repository untuk Mahasiswa (custom queries)
│
├── service/
│   ├── UserService.java                    # Interface untuk layanan autentikasi
│   ├── MahasiswaService.java               # Interface untuk layanan mahasiswa
│   └── impl/
│       ├── UserServiceImpl.java            # Implementasi registrasi, login, hashing BCrypt
│       └── MahasiswaServiceImpl.java       # Implementasi CRUD mahasiswa dengan transaksi
│
└── util/
    └── JwtUtil.java                        # Utility untuk generate dan validasi JWT token

src/main/resources/
├── application.properties                  # Konfigurasi database, JPA, server
├── static/
└── templates/
```

## Daftar Endpoint

| Method | URL | Autentikasi | Deskripsi |
|--------|-----|-------------|-----------|
| POST | `/api/auth/register` | Publik | Registrasi akun baru |
| POST | `/api/auth/login` | Publik | Login dan mendapatkan JWT |
| GET | `/api/mahasiswa` | JWT | Mengambil semua mahasiswa |
| GET | `/api/mahasiswa/{id}` | JWT | Mengambil mahasiswa berdasarkan ID |
| POST | `/api/mahasiswa` | JWT | Menambah mahasiswa baru |
| PATCH | `/api/mahasiswa/{id}` | JWT | Memperbarui data mahasiswa |
| DELETE | `/api/mahasiswa/{id}` | JWT | Menghapus mahasiswa |

## Tech Stack

| Teknologi | Versi | Kegunaan |
|-----------|-------|----------|
| Java | 17 | Bahasa pemrograman |
| Spring Boot | 3.5.14 | Framework utama (auto-configuration, embedded Tomcat) |
| Spring Data JPA | 3.5.11 | Akses database dengan JPA/Hibernate |
| Spring Security | 6.5.10 | Autentikasi dan otorisasi |
| Hibernate | 6.6.49 | ORM — mapping object Java ke tabel database |
| JJWT | 0.12.6 | Library untuk generate dan validasi JSON Web Token |
| Oracle JDBC | 23.7 | Driver koneksi ke Oracle Database |
| Lombok | 1.18.46 | Mengurangi kode boilerplate (getter, setter, constructor) |
| Jakarta Validation | 3.0.2 | Validasi input di sisi server |
| Jackson | 2.21 | Serialisasi/deserialisasi JSON |
| Maven | 3.9+ | Build tool dan dependency management |
| Oracle DB | 21c | Database relasional |
