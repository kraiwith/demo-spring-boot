package com.krai.demo_spring_boot.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequestDto {
  @NotBlank(message = "Name must not be blank")
  String name;

  @NotBlank(message = "Email must not be blank")
  @Email(message = "Email should be valid")
  String email;

  @NotBlank(message = "Password must not be blank")
  String password;

  public RegisterRequestDto(String name, String email, String password) {
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
