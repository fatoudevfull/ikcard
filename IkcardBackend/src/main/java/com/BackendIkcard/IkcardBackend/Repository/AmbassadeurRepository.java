package com.BackendIkcard.IkcardBackend.Repository;

import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import com.BackendIkcard.IkcardBackend.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AmbassadeurRepository extends JpaRepository<Ambassadeur, Long> {

    boolean existsByUsername(String username);
    Optional<Ambassadeur> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
