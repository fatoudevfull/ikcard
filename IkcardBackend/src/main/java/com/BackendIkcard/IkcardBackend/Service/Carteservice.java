package com.BackendIkcard.IkcardBackend.Service;





import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Carte;

import java.util.List;
import java.util.Optional;

public interface Carteservice {
    // Création d'un Carte
    ReponseMessage creerCarte(Carte carte);

    // Mise à jour d'un Annonce
    ReponseMessage modifierCarte (Carte carte);

    //affichage d'un Carte

    List<Carte> afficherToutLesCarte ();

    //Suppression d'un Annonce
    ReponseMessage SupprimerCarte (Long id );


    Carte saveOrUpdateCarte(Carte carte);

    Optional<Carte> getCarte(Long id);

    List<Carte> getCarte();

    void deleteCarteById(Long id);

}
