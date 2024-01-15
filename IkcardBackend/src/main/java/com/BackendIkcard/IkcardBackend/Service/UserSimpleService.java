package com.BackendIkcard.IkcardBackend.Service;



import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.UserSimple;
import com.BackendIkcard.IkcardBackend.Models.Users;

import java.util.List;
import java.util.Optional;

public interface UserSimpleService {

    // Création d'un UserSimple
    ReponseMessage creerUserSimple(UserSimple userSimple);

    UserSimple findById(Long userId);

    // Mise à jour d'un UserSimple
    ReponseMessage modifierUserSimple (Long id,UserSimple userSimple);

    //affichage d'un Administrateur

    List<UserSimple > afficherToutLesUserSimple ();

    List<Users> getUsersByUsername(String username);

    //Suppression d'un UserSimple
    ReponseMessage SupprimerUserSimple (Long id );
    //  public void activerUserSimple(Long id);

    void desactiverCompte(Long userId);
    Optional<UserSimple> findByUsername(String username);


    void activerCompte(Long userId);
    // User saveUserWithImage(User user, MultipartFile imageFile);
    List<Object> NombreAmbassadeur();
    List<Object> NombreUser();
    List<Object> NombreUserparpays(String pays);



}
