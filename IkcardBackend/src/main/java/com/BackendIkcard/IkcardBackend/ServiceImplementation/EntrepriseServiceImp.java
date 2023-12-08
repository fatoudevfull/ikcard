package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Administrateur;
import com.BackendIkcard.IkcardBackend.Models.Entreprise;
import com.BackendIkcard.IkcardBackend.Repository.AdminnistrateurRepository;
import com.BackendIkcard.IkcardBackend.Repository.EntrepriseRepository;
import com.BackendIkcard.IkcardBackend.Service.AdministrateurService;
import com.BackendIkcard.IkcardBackend.Service.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EntrepriseServiceImp implements EntrepriseService {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Override
    public ReponseMessage creerEntreprise(Entreprise entreprise) {
        if (entrepriseRepository.findByEmail(entreprise.getEmail()) == null) {
            // Set etat to true before saving
            entreprise.setEtat(true);
            entrepriseRepository.save(entreprise);
            return new ReponseMessage("Entreprise ajouté avec succès", true);
        } else {
            return new ReponseMessage("Cet Entreprise existe déjà", false);
        }
    }

    @Override
    public ReponseMessage modifierEntreprise(Long id, Entreprise entreprise) {
        Optional<Entreprise> existingAdminOptional = entrepriseRepository.findById(id);

        if (existingAdminOptional.isPresent()) {
            Entreprise existingAdmin = existingAdminOptional.get();
            // Set etat to true before updating
            entreprise.setEtat(true);
            existingAdmin.setNom(entreprise.getNom());
            existingAdmin.setNumero(entreprise.getNumero());
            existingAdmin.setPassword(entreprise.getPassword());
            existingAdmin.setImageCouverture(entreprise.getImageCouverture());
            existingAdmin.setPhoto(entreprise.getPhoto());
            entreprise.setEmail(entreprise.getEmail());
            entreprise.setAdresse(entreprise.getAdresse());
            // Set other fields as needed
            entrepriseRepository.save(existingAdmin);
            return new ReponseMessage("Entretpise modifié avec succès", true);
        } else {
            return new ReponseMessage("Désolé, entreprise non trouvé", false);
        }
    }


 /*   @Override
    public void activerEntreprise(Long id) {
        Administrateur administrateur = adminnistrateurRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Administrateur introuvable"));
        administrateur.setEtat(true);
        adminnistrateurRepository.save(administrateur);
    }*/

  /*  @Override
    public void activerEntreprise(Long id) {
        Optional<Entreprise> existingAdmin = entrepriseRepository.findById(id);
        existingAdmin.ifPresent(entreprise -> {
            // Set etat to true
            entreprise.setEtat(true);

            // Save the updated entity
            entrepriseRepository.save(entreprise);
        });
    }*/

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

    // Other methods...

}
