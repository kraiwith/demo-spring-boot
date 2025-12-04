package com.krai.demo_spring_boot.controllers;

import com.krai.demo_spring_boot.dtos.UserRequestDto;
import com.krai.demo_spring_boot.dtos.UserResponseDto;
import com.krai.demo_spring_boot.models.UserModel;
import com.krai.demo_spring_boot.repository.UserRepository;
import java.util.List;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/users")
  public List<UserResponseDto> getUsers() {
    return userRepository.findAll().stream()
        .map(
            userModel -> {
              return new UserResponseDto(
                  userModel.getId(), userModel.getName(), userModel.getEmail());
            })
        .toList();
  }

  @GetMapping("/user/{id}")
  public UserResponseDto getUserById(@PathVariable String id) {
    return userRepository
        .findById(id)
        .map(
            userModel -> {
              return new UserResponseDto(
                  userModel.getId(), userModel.getName(), userModel.getEmail());
            })
        .orElse(null);
  }

  @PostMapping("/user")
  public String addUser(@RequestBody UserRequestDto userDto) {
    UserModel userModel =
        new UserModel(userDto.getName(), userDto.getEmail(), userDto.getPassword());
    userRepository.save(userModel);
    return "User added successfully";
  }

  @PutMapping("/user/{id}")
  public String updateUser(@PathVariable String id, @RequestBody UserRequestDto userDto) {
    return userRepository
        .findById(id)
        .map(
            existingUser -> {
              existingUser.setName(userDto.getName());
              existingUser.setEmail(userDto.getEmail());
              userRepository.save(existingUser);
              return "User updated successfully";
            })
        .orElse("User not found");
  }

  @DeleteMapping("/user/{id}")
  public String deleteUser(@PathVariable String id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);
      return "User deleted successfully";
    } else {
      return "User not found";
    }
  }

  @GetMapping("/user/search")
  public List<UserResponseDto> getUserSearch(@RequestBody UserRequestDto userDto) {
    return userRepository.findAll().stream()
        .filter(
            userModel -> {
              boolean nameMatches = true;
              boolean emailMatches = true;

              if (StringUtils.hasText(userDto.getName())) {
                nameMatches = userModel.getName().contains(userDto.getName());
              }
              if (StringUtils.hasText(userDto.getEmail())) {
                emailMatches = userModel.getEmail().contains(userDto.getEmail());
              }
              return nameMatches && emailMatches;
            })
        .map(
            userModel -> {
              return new UserResponseDto(
                  userModel.getId(), userModel.getName(), userModel.getEmail());
            })
        .toList();
  }
}
