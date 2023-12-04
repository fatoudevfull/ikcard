package com.BackendIkcard.IkcardBackend.Repository;

import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AmbassadeurRepositoty extends JpaRepository<Ambassadeur, Long> {
    //Medecin findByNumero(String numero);
    Ambassadeur findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Ambassadeur> findByUsername(String username);

}
