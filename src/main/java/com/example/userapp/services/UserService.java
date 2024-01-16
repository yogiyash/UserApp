package com.example.userapp.services;

import com.example.userapp.dto.UserDTO;
import com.example.userapp.entity.Role;
import com.example.userapp.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(UserDTO userDto);

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    List<UserDTO> findAllUsers();

}
