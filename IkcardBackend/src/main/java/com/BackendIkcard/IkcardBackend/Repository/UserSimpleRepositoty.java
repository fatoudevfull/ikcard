package com.BackendIkcard.IkcardBackend.Repository;

import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import com.BackendIkcard.IkcardBackend.Models.UserSimple;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSimpleRepositoty extends JpaRepository<UserSimple, Long> {
    //Medecin findByNumero(String numero);
    UserSimple findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<UserSimple> findByUsername(String username);

}
