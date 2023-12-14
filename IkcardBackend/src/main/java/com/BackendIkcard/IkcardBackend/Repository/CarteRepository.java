package com.BackendIkcard.IkcardBackend.Repository;

import com.BackendIkcard.IkcardBackend.Models.Carte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CarteRepository extends JpaRepository<Carte, Long> {
    // Vos méthodes personnalisées du repository ici
    @Query(value ="SELECT * FROM `carte` WHERE carte.compagnie=:compagnie", nativeQuery = true )
    List<Object> ListeCarteparEntreprise(@Param("compagnie") String compagnie);
}
