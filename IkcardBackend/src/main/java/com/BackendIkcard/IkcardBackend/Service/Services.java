package com.BackendIkcard.IkcardBackend.Service;


import org.springframework.security.core.userdetails.User;

public interface Services {

    String modifierCollaborateur(User utilisateurs);
    /*
    List<Role> afficherRoles();
    String modifierRole(Role roles);
    String supprimerRole(Long id);
    String supprimerCollaborateur(Long id);

     */
}
