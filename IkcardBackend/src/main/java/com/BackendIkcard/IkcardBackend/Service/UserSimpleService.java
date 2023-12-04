package com.BackendIkcard.IkcardBackend.Service;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import com.BackendIkcard.IkcardBackend.Models.UserSimple;

import java.util.List;

public interface UserSimpleService {
    // Création d'un UserSimple
    ReponseMessage creerUserSimple(UserSimple userSimple);

    // Mise à jour d'un UserSimple
    ReponseMessage modifierUserSimple(UserSimple userSimple);

    //affichage d'un UserSimple

    List<UserSimple> afficherToutLesUserSimple();

    //Suppression d'un UserSimple
    ReponseMessage SupprimerUserSimple(Long id);

    int NombreUserSimple();
    List<Object> NombreUserSimpleParVille();
    List<UserSimple> NouveauUserSimple();

}
