package com.example.userapp.services;

import com.example.userapp.dto.UserDTO;
import com.example.userapp.entity.Role;
import com.example.userapp.entity.User;
import com.example.userapp.repository.RolesRepository;
import com.example.userapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDTO userDto){
        userRepository.save(dtoToUser(userDto));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }


    @Override
    public List<UserDTO> findAllUsers() {
       return userRepository.findAll().stream().map(this::userToDto).collect(Collectors.toList());
    }

    private UserDTO userToDto(User user){
        UserDTO dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setUsername(user.getName());
        dto.setPassword(user.getPassword());
        return dto;
    }

    private User dtoToUser(UserDTO userdto){
        User user = new User();
        user.setEmail(userdto.getEmail());
        user.setName(userdto.getName());
        user.setUsername(userdto.getUsername());
        Role role = rolesRepository.findByName("ROLE_USER");
        if(Objects.isNull(role)){
            role = saveRole("ROLE_USER");
        }
        user.setRoles(Arrays.asList(role));
        user.setPassword(passwordEncoder.encode(userdto.getPassword()));
        return user;
    }

    private Role saveRole(String name){
        Role role = new Role();
        role.setName(name);
        return rolesRepository.save(role);
    }


}
