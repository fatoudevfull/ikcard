package com.BackendIkcard.IkcardBackend.Service;



import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // Création d'un UserSimple
    ReponseMessage creerUserSimple(User userSimple);

    User findById(Long userId);

    // Mise à jour d'un UserSimple
    ReponseMessage modifierUserSimple (Long id,User userSimple);

    //affichage d'un Administrateur

    List<User > afficherToutLesUserSimple ();

    //Suppression d'un UserSimple
    ReponseMessage SupprimerUserSimple (Long id );
  //  public void activerUserSimple(Long id);

    void desactiverCompte(Long userId);

    void activerCompte(Long userId);
   // User saveUserWithImage(User user, MultipartFile imageFile);



}
