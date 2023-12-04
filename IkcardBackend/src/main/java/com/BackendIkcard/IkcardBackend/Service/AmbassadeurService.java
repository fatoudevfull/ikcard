package com.BackendIkcard.IkcardBackend.Service;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;

import java.util.List;

public interface AmbassadeurService {
    // Création d'un Ambassadeur
    ReponseMessage creerAmbassadeur(Ambassadeur ambassadeur);

    // Mise à jour d'un Ambassadeur
    ReponseMessage modifierAmbassadeur(Ambassadeur ambassadeur);

    //affichage d'un Ambassadeur

    List<Ambassadeur> afficherToutLesAmbassadeur();

    //Suppression d'un Ambassadeur
    ReponseMessage SupprimerAmbassadeur(Long id);

    int NombreAmbassadeur();
    List<Object> NombreAmbassadeurParVille();
    List<Ambassadeur> NouveauAmbassadeur();

}
