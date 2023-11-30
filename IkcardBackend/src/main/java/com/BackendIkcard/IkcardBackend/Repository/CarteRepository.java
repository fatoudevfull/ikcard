package com.BackendIkcard.IkcardBackend.Repository;


import com.BackendIkcard.IkcardBackend.Models.Carte;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarteRepository extends JpaRepository<Carte, Long> {
}
