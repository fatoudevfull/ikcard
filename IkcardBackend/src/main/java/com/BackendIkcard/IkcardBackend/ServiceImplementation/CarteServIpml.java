package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Administrateur;
import com.BackendIkcard.IkcardBackend.Models.Carte;
import com.BackendIkcard.IkcardBackend.Models.Contact;
import com.BackendIkcard.IkcardBackend.Repository.CarteRepository;
import com.BackendIkcard.IkcardBackend.Service.CarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarteServIpml implements CarteService {
   // private CarteRepository carteRepository;
   private final CarteRepository carteRepository;


    // Dans votre CarteService


    public CarteServIpml(CarteRepository carteRepository) {
        this.carteRepository = carteRepository;
    }

    public Carte creerCarte(Carte carte) {
        // Votre logique de création de carte ici
        // Set the current date
        carte.setDateCreationCarte(new Date());
        return carteRepository.save(carte);
    }

    // Dans votre CarteService
 /*   public Carte modifierCarte(Carte carte, Long id) {
        // Assurez-vous que la carte existe avant de la mettre à jour
        if (!carteRepository.existsById(id)) {
            throw new NoSuchElementException("Carte introuvable");
        }

        // Assurez-vous que l'ID de la carte dans le corps de la requête correspond à l'ID fourni dans l'URL
        if (!Objects.equals(carte.getId(), id)) {
            throw new IllegalArgumentException("L'ID de la carte ne correspond pas à l'ID fourni");
        }

        return carteRepository.save(carte);
    }*/
    public ReponseMessage modifierCarte(long carteId, Carte cartetModifie) {
        Carte carteExistant = carteRepository.findById(carteId)
                .orElseThrow(() -> new NoSuchElementException("Carte introuvable"));

        // Mettez à jour les champs du contact existant avec les nouvelles valeurs
        carteExistant.setNomComplet(cartetModifie.getNomComplet());
        carteExistant.setEmail(cartetModifie.getEmail());
        carteExistant.setFixe1(cartetModifie.getFixe1());
        carteExistant.setInstagramLink(cartetModifie.getInstagramLink());
        carteExistant.setLinkedinLink(cartetModifie.getLinkedinLink());
        // Ajoutez d'autres champs à mettre à jour

        carteRepository.save(carteExistant);

        return new ReponseMessage("Carte modifié avec succès", true);
    }

    //afficher tous les cartes
    public List<Carte> getAllCartes() {
        return carteRepository.findAll();
    }

  /*  @Override
    public void activerCarte(Long id) {
        Optional<Carte> existingAdmin = carteRepository.findById(id);
        existingAdmin.ifPresent(carte -> {
            // Set etat to true
            carte.setEtat(true);

            // Save the updated entity
            carteRepository.save(carte);
        });
    }*/
  public void desactiverCompte(Long userId) {
      Carte carte = carteRepository.findById(userId)
              .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

      carte.setEtat(false); // Mettez à false pour désactiver le compte
      carteRepository.save(carte);
  }

    public void activerCompte(Long userId) {
        Carte carte = carteRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        carte.setEtat(true); // Mettez à true pour activer le compte
        carteRepository.save(carte);
    }



    // Dans votre CarteService
    public Optional<Carte> afficherCarteParId(Long id) {
        return carteRepository.findById(id);
    }
    // Dans votre CarteService
    public void supprimerCarte(Long id) {
        // Assurez-vous que la carte existe avant de la supprimer
        if (carteRepository.existsById(id)) {
            carteRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Carte introuvable");
        }
    }


}
