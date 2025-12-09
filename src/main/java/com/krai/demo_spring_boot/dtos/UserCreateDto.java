package com.krai.demo_spring_boot.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCreateDto {
    @NotBlank()
    private String name;
    @NotBlank()
    @Email()
    private String email;
    @NotBlank()
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    public UserCreateDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
