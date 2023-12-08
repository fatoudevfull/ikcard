package com.BackendIkcard.IkcardBackend.Service;



import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Administrateur;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;

import java.util.List;

public interface AmbassadeurService {

    // Création d'un Ambassadeur
    ReponseMessage creerAmbassadeur(Ambassadeur ambassadeur);

    // Mise à jour d'un Ambassadeur
    ReponseMessage modifierAmbassadeur (Long id,Ambassadeur ambassadeur);

    //affichage d'un Ambassadeur

    List<Ambassadeur> afficherToutLesAmbassadeur ();

    //Suppression d'un Administrateur
    ReponseMessage SupprimerAmbassadeur (Long id );
    //public void activerAmbassadeur(Long id);
    void desactiverCompte(Long userId);

    void activerCompte(Long userId);



}
