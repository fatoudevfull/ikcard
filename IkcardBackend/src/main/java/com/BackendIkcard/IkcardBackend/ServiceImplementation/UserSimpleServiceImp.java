package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Administrateur;
import com.BackendIkcard.IkcardBackend.Models.UserSimple;
import com.BackendIkcard.IkcardBackend.Repository.AdminnistrateurRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserSimpleRepository;
import com.BackendIkcard.IkcardBackend.Service.AdministrateurService;
import com.BackendIkcard.IkcardBackend.Service.UserSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSimpleServiceImp implements UserSimpleService {

    @Autowired
    private UserSimpleRepository userSimpleRepository;

    @Override
    public ReponseMessage creerUserSimple(UserSimple userSimple) {
        if (userSimpleRepository.findByEmail(userSimple.getEmail()) == null) {
            // Set etat to true before saving
            userSimple.setEtat(true);
            userSimpleRepository.save(userSimple);
            return new ReponseMessage("Utilisateur ajouté avec succès", true);
        } else {
            return new ReponseMessage("Cet utilisateur existe déjà", false);
        }
    }

    @Override
    public ReponseMessage modifierUserSimple(Long id, UserSimple userSimple) {
        Optional<UserSimple> existingAdminOptional = userSimpleRepository.findById(id);

        if (existingAdminOptional.isPresent()) {
            UserSimple existingAdmin = existingAdminOptional.get();
            // Set etat to true before updating
            userSimple.setEtat(true);
            existingAdmin.setNom(userSimple.getNom());
            existingAdmin.setNumero(userSimple.getNumero());
            existingAdmin.setUsername(userSimple.getUsername());
            existingAdmin.setPassword(userSimple.getPassword());
            existingAdmin.setPrenom(userSimple.getPrenom());
          //  existingAdmin.setPhoto(userSimple.getPhoto());
            userSimple.setEmail(userSimple.getEmail());
            // Set other fields as needed
            userSimpleRepository.save(existingAdmin);
            return new ReponseMessage("Utilisateur modifié avec succès", true);
        } else {
            return new ReponseMessage("Désolé, Utilisateur non trouvé", false);
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
    public void activerUserSimple(Long id) {
        Optional<UserSimple> existingAdmin = userSimpleRepository.findById(id);
        existingAdmin.ifPresent(userSimple -> {
            // Set etat to true
            userSimple.setEtat(true);

            // Save the updated entity
            userSimpleRepository.save(userSimple);
        });
    }





    @Override
    public List<UserSimple> afficherToutLesUserSimple() {
        return userSimpleRepository.findAll();
    }

    @Override
    public ReponseMessage SupprimerUserSimple(Long id) {
        Optional<UserSimple> adminOptional = userSimpleRepository.findById(id);
        if (adminOptional.isPresent()) {
            UserSimple userSimple = adminOptional.get();
            userSimple.setEtat(false);
            userSimpleRepository.save(userSimple);
            return new ReponseMessage("Utilisateur supprimé avec succès", true);
        } else {
            return new ReponseMessage("Utilisateur non trouvé", false);
        }
    }

    // Other methods...

}
