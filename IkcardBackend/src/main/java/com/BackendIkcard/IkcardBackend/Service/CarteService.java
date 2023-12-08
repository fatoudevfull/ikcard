package com.BackendIkcard.IkcardBackend.Service;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Carte;
import com.BackendIkcard.IkcardBackend.Repository.CarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CarteService {

    Carte creerCarte(Carte carte);
   // Carte modifierCarte(Carte carte,Long id);
   ReponseMessage modifierCarte(long carteId, Carte cartetModifie);
    Optional<Carte> afficherCarteParId(Long id);
   void supprimerCarte(Long id);
    List<Carte> getAllCartes();
}
