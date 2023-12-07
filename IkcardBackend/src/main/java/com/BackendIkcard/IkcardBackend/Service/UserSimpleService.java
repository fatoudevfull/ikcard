package com.BackendIkcard.IkcardBackend.Service;



import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Administrateur;
import com.BackendIkcard.IkcardBackend.Models.UserSimple;

import java.util.List;

public interface UserSimpleService {

    // Création d'un UserSimple
    ReponseMessage creerUserSimple(UserSimple userSimple);

    // Mise à jour d'un UserSimple
    ReponseMessage modifierUserSimple (Long id,UserSimple userSimple);

    //affichage d'un Administrateur

    List<UserSimple > afficherToutLesUserSimple ();

    //Suppression d'un UserSimple
    ReponseMessage SupprimerUserSimple (Long id );
    public void activerUserSimple(Long id);



}
