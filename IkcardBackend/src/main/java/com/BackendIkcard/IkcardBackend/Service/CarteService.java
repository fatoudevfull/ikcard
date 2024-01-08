package com.BackendIkcard.IkcardBackend.Service;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Carte;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public interface CarteService {


    Carte creerCarteAvecNomPrenom(Carte carte, String username);

    // Carte modifierCarte(Carte carte,Long id);
   ReponseMessage modifierCarte(long carteId, Carte cartetModifie);
    Optional<Carte> afficherCarteParId(Long id);
   void supprimerCarte(Long id);
   // Carte enregistrerCarte(Carte carte, String username);
    List<Carte> getAllCartes();
  //  void activerCarte(Long id);
  void desactiverCompte(Long userId);

    void activerCompte(Long userId);
  //  String generateContentForUser(Optional<User> user);
    List<Object> ListeCarteparEntreprise(String compagnie);

    void ajouterphotoCouverture(Long id, MultipartFile photo);

}
