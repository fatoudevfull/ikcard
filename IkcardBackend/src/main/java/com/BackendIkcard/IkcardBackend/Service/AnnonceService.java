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
import com.BackendIkcard.IkcardBackend.Repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


public interface AnnonceService {



    public List<Annonce> getAllAnnonces() ;

    public Optional<Annonce> getAnnonceById(Long id) ;

    public Annonce createAnnonce(Annonce annonce);

    public Annonce updateAnnonce(Long id, Annonce updatedAnnonce) ;

    public void deleteAnnonce(Long id);
    public Annonce createAnnonceWithImage(String titre, String contenu, MultipartFile image, Long administrateurId);
}
