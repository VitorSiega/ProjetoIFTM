package com.example.projeto.seguranca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projeto.seguranca.enums.Role;
import com.example.projeto.seguranca.model.ModelRole;
import com.example.projeto.seguranca.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public ModelRole getOrCreateRole(Role roleName) {
        return roleRepository.findByName(roleName)
            .orElseGet(() -> roleRepository.save(new ModelRole(null, roleName)));
    }

}
