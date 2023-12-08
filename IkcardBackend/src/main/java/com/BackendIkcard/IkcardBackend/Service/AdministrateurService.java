package com.BackendIkcard.IkcardBackend.Service;



import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Administrateur;

import java.util.List;
import java.util.Optional;

public interface AdministrateurService {

    // Création d'un Administrateur
    ReponseMessage creerAdministrateur(Administrateur administrateur);

    // Mise à jour d'un Administrateur
    ReponseMessage modifierAdministrateur (Long id,Administrateur administrateur);

    //affichage d'un Administrateur

    List<Administrateur > afficherToutLesAdministrateur ();

    //Suppression d'un Administrateur
    ReponseMessage SupprimerAdministrateur (Long id );
   // public void activerAdmin(Long id);
   void desactiverCompte(Long userId);

    void activerCompte(Long userId);



}
