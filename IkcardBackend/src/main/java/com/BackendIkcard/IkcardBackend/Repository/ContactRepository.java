package com.BackendIkcard.IkcardBackend.Repository;


import com.BackendIkcard.IkcardBackend.Models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
   // Boolean existsByUsername(String username);

    //for email
    Optional<Contact> findByEmail(String email);
   // Boolean existsByEmail(String email);


  //  Boolean existsByNumero(String numero);

    //by telephone
  //  Contact findByNumero(String numero);
}
