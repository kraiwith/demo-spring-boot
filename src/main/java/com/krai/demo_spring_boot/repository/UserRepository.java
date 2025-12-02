package com.krai.demo_spring_boot.repository;

import com.krai.demo_spring_boot.models.UserModel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
  private List<UserModel> users =
      new ArrayList<>(
          List.of(
              new UserModel(UUID.randomUUID().toString(), "alice", "alice@mail.com", "password1"),
              new UserModel(UUID.randomUUID().toString(), "bob", "bob@mail.com", "password2"),
              new UserModel(
                  UUID.randomUUID().toString(), "charlie", "charlic@mail.com", "password3")));

  public List<UserModel> getUsers() {
    return users;
  }

  public void addUser(UserModel user) {
    users.add(user);
  }

  public boolean updateUser(UserModel user) {
    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getId().equals(user.getId())) {
        users.set(i, user);
        return true;
      }
    }
    return false;
  }

  public boolean removeUser(UserModel user) {
    return users.removeIf(u -> u.getId().equals(user.getId()));
  }
}
