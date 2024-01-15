package com.BackendIkcard.IkcardBackend.Service;



import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Entreprise;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EntrepriseService {

    // Création d'un Entreprise
    ReponseMessage creerEntreprise(Entreprise entreprise);
    void ajouterImageCouvertureEntreprise(Long entrepriseId, MultipartFile image);
    byte[] getImageCouvertureEntreprise(Long entrepriseId);



    // Mise à jour d'un Entreprise
    ReponseMessage modifierEntreprise (Long id, Entreprise entreprise);

    //affichage d'un Entreprise

    List<Entreprise> afficherToutLesEntreprise ();

    //Suppression d'un Entreprise
    ReponseMessage SupprimerEntreprise (Long id );
  //  public void activerEntreprise(Long id);
  void desactiverCompte(Long userId);

    void activerCompte(Long userId);



}
