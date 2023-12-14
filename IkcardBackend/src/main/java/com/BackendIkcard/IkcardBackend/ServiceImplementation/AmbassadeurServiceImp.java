package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import com.BackendIkcard.IkcardBackend.Repository.AmbassadeurRepository;
import com.BackendIkcard.IkcardBackend.Service.AmbassadeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    @Override
    public ReponseMessage modifierAmbassadeur(Long id, Ambassadeur ambassadeur) {
        Optional<Ambassadeur> existingAdminOptional = ambassadeurRepository.findById(id);

        if (existingAdminOptional.isPresent()) {
            Ambassadeur existingAdmin = existingAdminOptional.get();
            // Set etat to true before updating
            ambassadeur.setEtat(true);
            existingAdmin.setNom(ambassadeur.getNom());
            existingAdmin.setNumero(ambassadeur.getNumero());
            existingAdmin.setUsername(ambassadeur.getUsername());
            existingAdmin.setPassword(ambassadeur.getPassword());
            existingAdmin.setPrenom(ambassadeur.getPrenom());
            // existingAdmin.setPhoto(ambassadeur.getPhoto());
            ambassadeur.setEmail(ambassadeur.getEmail());
            // Set other fields as needed
            ambassadeurRepository.save(existingAdmin);
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
