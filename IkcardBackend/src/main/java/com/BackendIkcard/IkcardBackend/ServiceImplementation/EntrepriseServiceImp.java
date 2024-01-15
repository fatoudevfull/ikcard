package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Entreprise;
import com.BackendIkcard.IkcardBackend.Repository.EntrepriseRepository;
import com.BackendIkcard.IkcardBackend.Service.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
public class EntrepriseServiceImp implements EntrepriseService {

    @Autowired
    private EntrepriseRepository entrepriseRepository;


    @Override
    public ReponseMessage creerEntreprise(Entreprise entreprise) {
        if (entrepriseRepository.findByEmail(entreprise.getEmail()) == null) {
            // Set etat to true before saving
            entreprise.setEtat(true);
            // Set the current date
            entreprise.setDateCreationCompte(new Date());
            entrepriseRepository.save(entreprise);
            return new ReponseMessage("Entreprise ajouté avec succès", true);
        } else {
            return new ReponseMessage("Cet Entreprise existe déjà", false);
        }
    }

    @Override
    public ReponseMessage modifierEntreprise(Long id, Entreprise entreprise) {
        Optional<Entreprise> existingEntrepriseOptional = entrepriseRepository.findById(id);

        if (existingEntrepriseOptional.isPresent()) {
            Entreprise existingEntreprise = existingEntrepriseOptional.get();
            // Set etat to true before updating
            entreprise.setEtat(true);

            if (entreprise.getNom() != null) {
                existingEntreprise.setNom(entreprise.getNom());
            }

            if (entreprise.getNumero() != null) {
                existingEntreprise.setNumero(entreprise.getNumero());
            }


            if (entreprise.getImageCouverture() != null) {
                existingEntreprise.setImageCouverture(entreprise.getImageCouverture());
            }

            if (entreprise.getPhotoProfil() != null) {
                existingEntreprise.setPhotoProfil(entreprise.getPhotoProfil());
            }

            if (entreprise.getEmail() != null) {
                existingEntreprise.setEmail(entreprise.getEmail());
            }

            if (entreprise.getPays() != null) {
                existingEntreprise.setPays(entreprise.getPays());
            }

            if (entreprise.getVille() != null) {
                existingEntreprise.setVille(entreprise.getVille());
            }

            if (entreprise.getAdresse() != null) {
                existingEntreprise.setAdresse(entreprise.getAdresse());
            }


            entrepriseRepository.save(existingEntreprise);
            return new ReponseMessage("Entreprise modifié avec succès", true);
        } else {
            return new ReponseMessage("Désolé, entreprise non trouvé", false);
        }
    }



    @Override
    public void ajouterImageCouvertureEntreprise(Long entrepriseId, MultipartFile image) {
        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new EntityNotFoundException("Entreprise non trouvée avec l'ID : " + entrepriseId));

        try {
            entreprise.setImageCouvertureType(image.getContentType());
            entreprise.setImageCouverture(image.getOriginalFilename());
            entreprise.setImageCouvertureData(image.getBytes());

            entrepriseRepository.save(entreprise);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'ajout de l'image de couverture à l'entreprise.", e);
        }
    }

    @Override
    public byte[] getImageCouvertureEntreprise(Long entrepriseId) {
        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new EntityNotFoundException("Entreprise non trouvée avec l'ID : " + entrepriseId));

        return entreprise.getImageCouvertureData();
    }

    public void desactiverCompte(Long userId) {
        Entreprise entreprise = entrepriseRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        entreprise.setEtat(false); // Mettez à false pour désactiver le compte
        entrepriseRepository.save(entreprise);
    }

    public void activerCompte(Long userId) {
        Entreprise entreprise = entrepriseRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        entreprise.setEtat(true); // Mettez à true pour activer le compte
        entrepriseRepository.save(entreprise);
    }


    @Override
    public List<Entreprise> afficherToutLesEntreprise() {
        return entrepriseRepository.findAll();
    }

    @Override
    public ReponseMessage SupprimerEntreprise(Long id) {
        Optional<Entreprise> adminOptional = entrepriseRepository.findById(id);
        if (adminOptional.isPresent()) {
            Entreprise entreprise = adminOptional.get();
            entreprise.setEtat(false);
            entrepriseRepository.save(entreprise);
            return new ReponseMessage("entreprise supprimé avec succès", true);
        } else {
            return new ReponseMessage("entreprise non trouvé", false);
        }
    }
}
