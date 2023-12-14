package com.BackendIkcard.IkcardBackend.Repository;


import com.BackendIkcard.IkcardBackend.Models.Administrateur;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AmbassadeurRepository extends JpaRepository<Ambassadeur, Long> {

    Ambassadeur findByEmail(String email);

   // boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);



   // Optional<Administrateur> findByUsername(String username);


}
