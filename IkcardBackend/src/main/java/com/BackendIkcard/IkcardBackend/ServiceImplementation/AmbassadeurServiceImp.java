package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Administrateur;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import com.BackendIkcard.IkcardBackend.Repository.AdminnistrateurRepository;
import com.BackendIkcard.IkcardBackend.Repository.AmbassadeurRepository;
import com.BackendIkcard.IkcardBackend.Service.AdministrateurService;
import com.BackendIkcard.IkcardBackend.Service.AmbassadeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        }    }

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


 /*   @Override
    public void activerAdmin(Long id) {
        Administrateur administrateur = adminnistrateurRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Administrateur introuvable"));
        administrateur.setEtat(true);
        adminnistrateurRepository.save(administrateur);
    }*/

    @Override
    public void activerAmbassadeur(Long id) {
        Optional<Ambassadeur> existingAdmin = ambassadeurRepository.findById(id);
        existingAdmin.ifPresent(administrateur -> {
            // Set etat to true
            administrateur.setEtat(true);

            // Save the updated entity
            ambassadeurRepository.save(administrateur);
        });
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

    // Other methods...

}
