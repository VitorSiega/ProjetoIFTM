package com.example.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projeto.enums.Role;
import com.example.projeto.model.ModelRole;
import com.example.projeto.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public ModelRole getOrCreateRole(Role roleName) {
        return roleRepository.findByName(roleName)
            .orElseGet(() -> roleRepository.save(new ModelRole(null, roleName)));
    }
}
