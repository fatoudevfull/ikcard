package com.BackendIkcard.IkcardBackend.Service;



import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.User;

import java.util.List;

public interface UserService {

    // Création d'un UserSimple
    ReponseMessage creerUserSimple(User userSimple);

    // Mise à jour d'un UserSimple
    ReponseMessage modifierUserSimple (Long id,User userSimple);

    //affichage d'un Administrateur

    List<User > afficherToutLesUserSimple ();

    //Suppression d'un UserSimple
    ReponseMessage SupprimerUserSimple (Long id );
  //  public void activerUserSimple(Long id);

    void desactiverCompte(Long userId);

    void activerCompte(Long userId);



}
