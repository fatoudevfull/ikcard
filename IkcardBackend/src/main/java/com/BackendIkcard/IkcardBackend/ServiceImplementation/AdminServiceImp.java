package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Administrateur;
import com.BackendIkcard.IkcardBackend.Repository.AdminnistrateurRepository;
import com.BackendIkcard.IkcardBackend.Service.AdministrateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AdminServiceImp implements AdministrateurService {

    @Autowired
    private AdminnistrateurRepository adminnistrateurRepository;

    @Override
    public ReponseMessage creerAdministrateur(Administrateur administrateur) {
        if (adminnistrateurRepository.findByEmail(administrateur.getEmail()) == null) {
            // Set etat to true before saving
            administrateur.setEtat(true);
            adminnistrateurRepository.save(administrateur);
            return new ReponseMessage ("Administrateur ajouté avec succès", true);
        } else {
            return new ReponseMessage("Cet administrateur existe déjà", false);
        }
    }

    @Override
    public ReponseMessage modifierAdministrateur(Long id, Administrateur administrateur) {
        Optional<Administrateur> existingAdminOptional = adminnistrateurRepository.findById(id);

        if (existingAdminOptional.isPresent()) {
            Administrateur existingAdmin = existingAdminOptional.get();

            // Set etat to true before updating
            existingAdmin.setEtat(true);

            // Update only non-null properties
            if (administrateur.getNom() != null) {
                existingAdmin.setNom(administrateur.getNom());
            }
            if (administrateur.getNumero() != null) {
                existingAdmin.setNumero(administrateur.getNumero());
            }
            if (administrateur.getPays() != null) {
                existingAdmin.setPays(administrateur.getPays());
            }
            if (administrateur.getVille() != null) {
                existingAdmin.setVille(administrateur.getVille());
            }
            if (administrateur.getPrenom() != null) {
                existingAdmin.setPrenom(administrateur.getPrenom());
            }
            if (administrateur.getPhotoProfil() != null) {
                existingAdmin.setPhotoProfil(administrateur.getPhotoProfil());
            }
            if (administrateur.getEmail() != null) {
                existingAdmin.setEmail(administrateur.getEmail());
            }


            adminnistrateurRepository.save(existingAdmin);
            return new ReponseMessage("Administrateur modifié avec succès", true);
        } else {
            return new ReponseMessage("Désolé, administrateur non trouvé", false);
        }
    }



 public void desactiverCompte(Long userId) {
     Administrateur administrateur = adminnistrateurRepository.findById(userId)
             .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

     administrateur.setEtat(false); // Mettez à false pour désactiver le compte
     adminnistrateurRepository.save(administrateur);
 }

    public void activerCompte(Long userId) {
        Administrateur administrateur = adminnistrateurRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        administrateur.setEtat(true); // Mettez à true pour activer le compte
        adminnistrateurRepository.save(administrateur);
    }




    @Override
    public List<Administrateur> afficherToutLesAdministrateur() {
        return adminnistrateurRepository.findAll();
    }

    @Override
    public ReponseMessage SupprimerAdministrateur(Long id) {
        Optional<Administrateur> adminOptional = adminnistrateurRepository.findById(id);
        if (adminOptional.isPresent()) {
            Administrateur administrateur = adminOptional.get();
            administrateur.setEtat(false);
            adminnistrateurRepository.save(administrateur);
            return new ReponseMessage("Administrateur supprimé avec succès", true);
        } else {
            return new ReponseMessage("Administrateur non trouvé", false);
        }
    }

    // Other methods...

}
