package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import com.BackendIkcard.IkcardBackend.Repository.AmbassadeurRepository;
import com.BackendIkcard.IkcardBackend.Service.AmbassadeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AmbassadeurServiceImp implements AmbassadeurService {

    @Autowired
    private AmbassadeurRepository ambassadeurRepository;


    @Override
    public ReponseMessage creerAmbassadeur(Ambassadeur ambassadeur) {
        if (ambassadeurRepository.findByEmail(ambassadeur.getEmail()) == null) {
            // Set etat to true before saving
            ambassadeur.setEtat(true);
            ambassadeurRepository.save(ambassadeur);
            return new ReponseMessage("Ambassadeur ajouté avec succès", true);
        } else {
            return new ReponseMessage("Cet Ambassadeur existe déjà", false);
        }
    }

    public Ambassadeur creerAmbassadeurL(Ambassadeur ambassadeur) {
        // Logique pour générer le lien de référencement automatiquement
        ambassadeur.setLienReferencement(genererLienReferencementUnique());
        return ambassadeurRepository.save(ambassadeur);
    }

    private String genererLienReferencementUnique() {
        // Logique de génération du lien de référencement unique
        // Vous pouvez utiliser UUID ou une autre méthode pour garantir l'unicité
        return UUID.randomUUID().toString();
    }
    @Override
    public ReponseMessage modifierAmbassadeur(Long id, Ambassadeur ambassadeur) {
        Optional<Ambassadeur> existingAbsOptional = ambassadeurRepository.findById(id);

        if (existingAbsOptional.isPresent()) {
            Ambassadeur existingAbs = existingAbsOptional.get();

            // Set etat to true before updating
            existingAbs.setEtat(true);

            // Update only non-null properties
            if (ambassadeur.getNom() != null) {
                existingAbs.setNom(ambassadeur.getNom());
            }
            if (ambassadeur.getNumero() != null) {
                existingAbs.setNumero(ambassadeur.getNumero());
            }
            if (ambassadeur.getUsername() != null) {
                existingAbs.setUsername(ambassadeur.getUsername());
            }
            if (ambassadeur.getPassword() != null) {
                existingAbs.setPassword(ambassadeur.getPassword());
            }
            if (ambassadeur.getPrenom() != null) {
                existingAbs.setPrenom(ambassadeur.getPrenom());
            }
            if (ambassadeur.getEmail() != null) {
                existingAbs.setEmail(ambassadeur.getEmail());
            }

            // Set other fields as needed

            ambassadeurRepository.save(existingAbs);
            return new ReponseMessage("Ambassadeur modifié avec succès", true);
        } else {
            return new ReponseMessage("Désolé, Ambassadeur non trouvé", false);
        }
    }


    public void desactiverCompte(Long userId) {
        Ambassadeur user = ambassadeurRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        user.setEtat(false); // Mettez à false pour désactiver le compte
        ambassadeurRepository.save(user);
    }

    public void activerCompte(Long userId) {
        Ambassadeur user = ambassadeurRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        user.setEtat(true); // Mettez à true pour activer le compte
        ambassadeurRepository.save(user);
    }

    @Override
    public List<Ambassadeur> afficherToutLesAmbassadeur() {
        return ambassadeurRepository.findAll();
    }
    @Override
    public ReponseMessage SupprimerAmbassadeur(Long id) {
        Optional<Ambassadeur> adminOptional = ambassadeurRepository.findById(id);
        if (adminOptional.isPresent()) {
            Ambassadeur ambassadeur = adminOptional.get();
            ambassadeur.setEtat(false);
            ambassadeurRepository.save(ambassadeur);
            return new ReponseMessage("Ambassadeur supprimé avec succès", true);
        } else {
            return new ReponseMessage("Ambassadeur non trouvé", false);
        }
    }



}
