package com.BackendIkcard.IkcardBackend.Service;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.User;
import com.BackendIkcard.IkcardBackend.Models.UserSimple;

import java.util.List;

public interface UserSimpleService {
    // Création d'un UserSimple
    ReponseMessage creerUserSimple(UserSimple userSimple);
    User saveUserSimple(UserSimple user);

    // Mise à jour d'un UserSimple
    ReponseMessage modifierUserSimple(UserSimple userSimple);

    // Affichage de tous les UserSimple
    List<UserSimple> afficherToutLesUserSimple();

    // Suppression d'un UserSimple
    ReponseMessage SupprimerUserSimple(Long id);

    // Obtention du nombre de UserSimple
    int NombreUserSimple();

    // Obtention du nombre de UserSimple par ville
    List<Object> NombreUserSimpleParVille();

    // Obtention de la liste des nouveaux UserSimple
    List<UserSimple> NouveauUserSimple();
}
