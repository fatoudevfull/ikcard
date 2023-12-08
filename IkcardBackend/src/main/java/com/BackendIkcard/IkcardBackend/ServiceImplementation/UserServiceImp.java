package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.User;
import com.BackendIkcard.IkcardBackend.Repository.UserRepository;
import com.BackendIkcard.IkcardBackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userSimpleRepository;

    @Override
    public ReponseMessage creerUserSimple(User userSimple) {
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
    public ReponseMessage modifierUserSimple(Long id, User userSimple) {
        Optional<User> existingAdminOptional = userSimpleRepository.findById(id);

        if (existingAdminOptional.isPresent()) {
            User existingAdmin = existingAdminOptional.get();
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

/*    @Override
    public void activerUserSimple(Long id) {
        Optional<User> existingAdmin = userSimpleRepository.findById(id);
        existingAdmin.ifPresent(userSimple -> {
            // Set etat to true
            userSimple.setEtat(true);

            // Save the updated entity
            userSimpleRepository.save(userSimple);
        });
    }*/

    public void desactiverCompte(Long userId) {
        User user = userSimpleRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        user.setEtat(false); // Mettez à false pour désactiver le compte
        userSimpleRepository.save(user);
    }

    public void activerCompte(Long userId) {
        User user = userSimpleRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        user.setEtat(true); // Mettez à true pour activer le compte
        userSimpleRepository.save(user);
    }





    @Override
    public List<User> afficherToutLesUserSimple() {
        return userSimpleRepository.findAll();
    }

    @Override
    public ReponseMessage SupprimerUserSimple(Long id) {
        Optional<User> adminOptional = userSimpleRepository.findById(id);
        if (adminOptional.isPresent()) {
            User userSimple = adminOptional.get();
            userSimple.setEtat(false);
            userSimpleRepository.save(userSimple);
            return new ReponseMessage("Utilisateur supprimé avec succès", true);
        } else {
            return new ReponseMessage("Utilisateur non trouvé", false);
        }
    }

    // Other methods...

}
