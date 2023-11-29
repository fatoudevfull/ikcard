package com.BackendIkcard.IkcardBackend.Service;





import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Entreprise;

import java.util.List;
import java.util.Optional;

public interface EntrepriseService {

    // Création d'un Entreprise
    ReponseMessage creerEntreprise (Entreprise entreprise);

    // Mise à jour d'un Entreprise
    ReponseMessage modifierEntreprise (Entreprise entreprise);

    //affichage d'un Entreprise

    List<Entreprise > afficherToutLesEntreprise();

    //Suppression d'un Entreprise
    ReponseMessage SupprimerEntreprise (Long idEntreprise );

    Entreprise saveOrUpdateEntreprise(Entreprise entreprise);

    Optional<Entreprise> getEntrepriseById(Long id);

    List<Entreprise> getAllEntreprise();


}
