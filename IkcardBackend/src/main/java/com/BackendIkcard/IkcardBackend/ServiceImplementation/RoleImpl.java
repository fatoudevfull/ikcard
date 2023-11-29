package com.BackendIkcard.IkcardBackend.ServiceImplementation;


import com.BackendIkcard.IkcardBackend.Models.ERole;
import com.BackendIkcard.IkcardBackend.Models.Role;
import com.BackendIkcard.IkcardBackend.Repository.RoleRepository;
import com.BackendIkcard.IkcardBackend.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleImpl implements RoleService {


    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        // TODO Auto-generated method stub
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Role role) {
        // TODO Auto-generated method stub
        return roleRepository.save(role);
    }

    @Override
    public Role getRole(Long id) {
        // TODO Auto-generated method stub
        return roleRepository.findById(id).get();
    }

    @Override
    public void deleteRole(Role role) {
        // TODO Auto-generated method stub
        roleRepository.delete(role);
    }

    @Override
    public List<Role> getAllRole() {
        // TODO Auto-generated method stub
        return roleRepository.findAll();
    }

    @Override
    public Role parNomRole(ERole role) {
        // TODO Auto-generated method stub
        return roleRepository.findByName(role);
    }
}
