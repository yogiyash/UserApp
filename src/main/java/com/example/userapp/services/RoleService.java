package com.example.userapp.services;

import com.example.userapp.entity.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
     Role saveRole(Role role);
}
