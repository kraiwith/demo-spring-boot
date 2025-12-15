package com.krai.demo_spring_boot.controllers;

import com.krai.demo_spring_boot.dtos.LoginRequestDto;
import com.krai.demo_spring_boot.dtos.LoginResponseDto;
import com.krai.demo_spring_boot.dtos.RegisterRequestDto;
import com.krai.demo_spring_boot.dtos.UserResponseDto;
import com.krai.demo_spring_boot.models.UserModel;
import com.krai.demo_spring_boot.repository.UserRepository;
import com.krai.demo_spring_boot.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AuthController {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public AuthController(
      UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/login")
  public LoginResponseDto login(@Valid @RequestBody LoginRequestDto body) {
    UserModel user =
        this.userRepository
            .findByEmail(body.getEmail())
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid email or password"));
    if (!passwordEncoder.matches(body.getPassword(), user.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }

    String token = jwtUtil.generateToken(user.getEmail());

    return new LoginResponseDto(token, "Login successful");
  }

  @GetMapping("/profile")
  public UserResponseDto getProfile(HttpServletRequest request) {
    String email = (String) request.getAttribute("email");
    UserModel user = this.userRepository
        .findByEmail(email)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    return new UserResponseDto(user.getId(), user.getName(), user.getEmail());
  }

  @PostMapping("/register")
  public String register(@Valid @RequestBody RegisterRequestDto body) {
    if (this.userRepository.findByEmail(body.getEmail()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
    }

    String hashedPassword = passwordEncoder.encode(body.getPassword());
    UserModel newUser = new UserModel(body.getName(), body.getEmail(), hashedPassword);
    this.userRepository.save(newUser);

    return "Registration successful";
  }  
}
