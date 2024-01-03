/*
package com.BackendIkcard.IkcardBackend.Service;



import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Annonce;

import java.util.List;
import java.util.Optional;

public interface AnnonceService {
    // Création d'un Annonce
    ReponseMessage creerAnnonce(Annonce annonce);

    // Mise à jour d'un Annonce
    ReponseMessage modifierAnnonce (Annonce annonce);

    //affichage d'un Annonce

    List<Annonce > afficherToutLesAnnonce ();

    //Suppression d'un Annonce
    ReponseMessage SupprimerAnnonce (Long id );


    Annonce saveOrUpdateAnnonce(Annonce annonce);

    Optional<Annonce> getAnnonce(Long id);

    List<Annonce> getAnnonce();

    void deleteAnnonceById(Long id);
}
*/
package com.BackendIkcard.IkcardBackend.Service;

import com.BackendIkcard.IkcardBackend.Models.Annonce;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface AnnonceService {



    List<Annonce> getAllAnnonces();

    Annonce getAnnonceById(Long id);

    Annonce createAnnonce(Annonce annonce);

    Annonce updateAnnonce(Long id, Annonce updatedAnnonce);

    void deleteAnnonce(Long id);
    void desactiverCompte(Long userId);

    void activerCompte(Long userId);



}
