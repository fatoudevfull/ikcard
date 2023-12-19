package com.BackendIkcard.IkcardBackend.Repository;


import com.BackendIkcard.IkcardBackend.Models.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminnistrateurRepository extends JpaRepository<Administrateur, Long> {

    Administrateur findByEmail(String email);

   // boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByNumero(String numero);



   // Optional<Administrateur> findByUsername(String username);


}
