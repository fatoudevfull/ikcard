package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Entreprise;
import com.BackendIkcard.IkcardBackend.Models.User;
import com.BackendIkcard.IkcardBackend.Repository.EntrepriseRepository;
import com.BackendIkcard.IkcardBackend.Service.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EntrepriseServImp implements EntrepriseService {

    @Autowired
    EntrepriseRepository entrepriseRepository;

    @Override
    public ReponseMessage creerEntreprise(Entreprise entreprise) {
        if (entrepriseRepository.findByNom(entreprise.getNom()) == null) {
            entrepriseRepository.save(entreprise);
            return new ReponseMessage("Entreprise ajoutée avec succès", true);
        } else {
            return new ReponseMessage("Cette entreprise existe déjà", false);
        }
    }

    @Override
    public void activerEntreprise(Long id) {
        Optional<Entreprise> existingAdmin = entrepriseRepository.findById(id);
        existingAdmin.ifPresent(administrateur -> {
            // Set etat to true
            administrateur.setEtat(true);

            // Save the updated entity
            entrepriseRepository.save(administrateur);
        });
    }


    @Override
    public Entreprise saveUser(Entreprise entreprise) {
        return entrepriseRepository.save(entreprise);
    }

    @Override
    public Entreprise saveEntreprise(Entreprise entreprise) {
        return null;
    }

    @Override
    public ReponseMessage modifierEntreprise(Entreprise entreprise) {
        // Implement modification logic
        return null;
    }

    @Override
    public List<Entreprise> afficherToutLesEntreprise() {
        return entrepriseRepository.findAll();
    }

 /*   @Override
    public void activerEntreprise(Long id) {
        try {
            Entreprise entreprise = entrepriseRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Entreprise introuvable"));
            entreprise.setEtat(true);
            entrepriseRepository.save(entreprise);
        } catch (Exception e) {
            // Log or handle the exception
            e.printStackTrace();
            // Provide a meaningful error message in the response
            throw new RuntimeException("Erreur lors de l'activation de l'entreprise");
        }
    }*/

    @Override
    public ReponseMessage SupprimerEntreprise(Long idEntreprise) {
        try {
            entrepriseRepository.findById(idEntreprise)
                    .ifPresent(entreprise -> {
                        entreprise.setEtat(false);
                        entrepriseRepository.save(entreprise);
                    });
            return new ReponseMessage("Entreprise supprimée avec succès", true);
        } catch (Exception e) {
            // Log or handle the exception
            e.printStackTrace();
            // Provide a meaningful error message in the response
            return new ReponseMessage("Erreur lors de la suppression de l'entreprise", false);
        }
    }

    @Override
    public Entreprise saveOrUpdateEntreprise(Entreprise entreprise) {
        return null;
    }

    @Override
    public Optional<Entreprise> getEntrepriseById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Entreprise> getAllEntreprise() {
        return null;
    }

    // Implementations for other methods...

}
