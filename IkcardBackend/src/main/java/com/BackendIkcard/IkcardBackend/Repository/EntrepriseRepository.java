package com.BackendIkcard.IkcardBackend.Repository;


import com.BackendIkcard.IkcardBackend.Models.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
    Entreprise findByEmail(String email);

    boolean existsByEmail(String email);
    Entreprise findByNom(String nom);
    Entreprise findByEmailIgnoreCase(String email);
    Entreprise findByEmailAndPassword(String email, String password);


}
