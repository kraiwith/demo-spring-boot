package com.krai.demo_spring_boot.controllers;

import com.krai.demo_spring_boot.dtos.UserRequestDto;
import com.krai.demo_spring_boot.dtos.UserResponseDto;
import com.krai.demo_spring_boot.models.UserModel;
import com.krai.demo_spring_boot.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class UserController {

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/users")
  public List<UserResponseDto> getUsers() {
    return userRepository.getUsers().stream()
        .map(
            userModel -> {
              return new UserResponseDto(
                  userModel.getId(), userModel.getName(), userModel.getEmail());
            })
        .toList();
  }

  @GetMapping("/user/{id}")
  public UserResponseDto getUserById(@PathVariable String id) {
    return userRepository.getUsers().stream()
        .filter(userModel -> userModel.getId().equals(id))
        .findFirst()
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
        new UserModel(UUID.randomUUID().toString(), userDto.getName(), userDto.getEmail(), userDto.getPassword());
    userRepository.addUser(userModel);
    return "User added successfully";
  }

  @PutMapping("/user/{id}")
  public String updateUser(@PathVariable String id, @RequestBody UserRequestDto userDto) {
    UserModel userModel =
        new UserModel(id, userDto.getName(), userDto.getEmail());
    boolean isSuccess = userRepository.updateUser(userModel);
    if (isSuccess) {
      return "User updated successfully";
    } else {
      return "User not found";
    }
  }

  @DeleteMapping("/user/{id}")
  public String deleteUser(@PathVariable String id) {
    UserModel userModel = new UserModel(id, "", "", "");
    boolean isDelete = userRepository.removeUser(userModel);
    if (isDelete) {
      return "User deleted successfully";
    } else {
      return "User not found";
    }
  }

}
