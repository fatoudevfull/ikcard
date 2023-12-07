package com.BackendIkcard.IkcardBackend.Repository;


import com.BackendIkcard.IkcardBackend.Models.Administrateur;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmbassadeurRepository extends JpaRepository<Ambassadeur, Long> {

    Ambassadeur findByEmail(String email);

   // boolean existsByUsername(String username);
    boolean existsByEmail(String email);



   // Optional<Administrateur> findByUsername(String username);


}
