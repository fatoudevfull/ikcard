package com.BackendIkcard.IkcardBackend.Service;



import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Administrateur;

import java.util.List;
import java.util.Optional;

public interface AdministrateurService {

    // Création d'un Administrateur
    ReponseMessage creerAdministrateur(Administrateur administrateur);

    // Mise à jour d'un Administrateur
    ReponseMessage modifierAdministrateur (Administrateur administrateur);

    //affichage d'un Administrateur

    List<Administrateur > afficherToutLesAdministrateur ();

    //Suppression d'un Administrateur
    ReponseMessage SupprimerAdministrateur (Long idAdministrateur );


    Administrateur saveOrUpdateAdministrateur(Administrateur administrateur);

    Optional<Administrateur> getAdministrateur(Long id);

    List<Administrateur> getAdministrateur();

    void deleteAdministrateurById(Long id);
}
