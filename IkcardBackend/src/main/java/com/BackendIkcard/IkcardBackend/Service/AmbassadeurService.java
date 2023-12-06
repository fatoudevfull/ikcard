package com.BackendIkcard.IkcardBackend.Service;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;

import java.util.List;

public interface AmbassadeurService {
    // Création d'un Ambassadeur
  //  ReponseMessage creerAmbassadeur(Ambassadeur ambassadeur);
    Ambassadeur saveUser(Ambassadeur ambassadeur);

    // Mise à jour d'un Ambassadeur
    ReponseMessage modifierAmbassadeur(Ambassadeur ambassadeur);

    // Affichage de tous les Ambassadeurs
    List<Ambassadeur> afficherToutLesAmbassadeur();

    // Suppression d'un Ambassadeur
    ReponseMessage SupprimerAmbassadeur(Long id);

    // Obtention du nombre d'Ambassadeurs
    int NombreAmbassadeur();

    // Obtention du nombre d'Ambassadeurs par ville
    List<Object> NombreAmbassadeurParVille();

    // Obtention de la liste des nouveaux Ambassadeurs
    List<Ambassadeur> NouveauAmbassadeur();
}
