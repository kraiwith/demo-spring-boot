package com.krai.demo_spring_boot.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserUpdateDto {
    @NotBlank()
    private String name;
    @NotBlank()
    @Email()
    private String email;

    public UserUpdateDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
