package com.example.userapp.services;

import com.example.userapp.entity.Role;
import com.example.userapp.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

public class RoleServiceImpl implements RoleService{

    @Autowired
    private RolesRepository rolesRepository;
    @Override
    public Role saveRole(Role role) {
        return rolesRepository.save(role);
    }

    public List<Role> findAllRoles(){
        return rolesRepository.findAll();
    }

    public Role findRoleByName(String name){
        return  rolesRepository.findByName(name);
    }
}
