package com.BackendIkcard.IkcardBackend.Repository;


import com.BackendIkcard.IkcardBackend.Models.UserSimple;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSimpleRepository extends JpaRepository<UserSimple, Long> {

    UserSimple findByEmail(String email);

   // boolean existsByUsername(String username);
    boolean existsByEmail(String email);



   // Optional<Administrateur> findByUsername(String username);


}
