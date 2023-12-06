package com.BackendIkcard.IkcardBackend.Service;





import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import com.BackendIkcard.IkcardBackend.Models.Entreprise;
import com.BackendIkcard.IkcardBackend.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
public interface EntrepriseService {

    // Création d'un Entreprise
    ReponseMessage creerEntreprise (Entreprise entreprise);
    Entreprise saveUser(Entreprise entreprise);

     Entreprise saveEntreprise(Entreprise entreprise);

    // Mise à jour d'un Entreprise
    ReponseMessage modifierEntreprise (Entreprise entreprise);

    //affichage d'un Entreprise

    List<Entreprise > afficherToutLesEntreprise();

    void activerEntreprise(Long id);


    //Suppression d'un Entreprise
    ReponseMessage SupprimerEntreprise (Long id);

    Entreprise saveOrUpdateEntreprise(Entreprise entreprise);

    Optional<Entreprise> getEntrepriseById(Long id);

    List<Entreprise> getAllEntreprise();


}
