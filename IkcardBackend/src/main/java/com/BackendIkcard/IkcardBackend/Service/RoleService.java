package com.BackendIkcard.IkcardBackend.Service;





import com.BackendIkcard.IkcardBackend.Models.ERole;
import com.BackendIkcard.IkcardBackend.Models.Role;

import java.util.List;

public interface RoleService {
    // Methode pour la création d'un role
    Role saveRole(Role role);

    // Methode pour la modification d'un role
    Role updateRole(Role role);

    // Methode pour la recuperation d'un role
    Role getRole(Long id);

    // Methode pour la surpression d'un role à partir d'un role
    void deleteRole(Role role);

    // Methode pour la liste des roles à partir d'un role
    List<Role> getAllRole();

    //Retrouver par nom
    Role parNomRole(ERole role);
}
