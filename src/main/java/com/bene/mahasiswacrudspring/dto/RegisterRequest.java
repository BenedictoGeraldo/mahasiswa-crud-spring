package com.bene.mahasiswacrudspring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Username wajib diisi")
    @Size(min = 3, max = 50, message = "Username harus 3-50 karakter")
    private String username;

    @NotBlank(message = "Password wajib diisi")
    @Size(min = 6, max = 100, message = "Password harus 6-100 karakter")
    private String password;
}
