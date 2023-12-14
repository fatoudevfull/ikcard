package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.User;
import com.BackendIkcard.IkcardBackend.Repository.UserRepository;
import com.BackendIkcard.IkcardBackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
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
            // Set the current date
            userSimple.setDateCreationCompte(new Date());
            return new ReponseMessage("Cet utilisateur existe déjà", false);
        }
    }


    @Override
    public User findById(Long userId) {
        return userSimpleRepository.findById(userId).orElse(null);
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
            existingAdmin.setAdresse(userSimple.getAdresse());
            existingAdmin.setPassword(userSimple.getPassword());
            existingAdmin.setPrenom(userSimple.getPrenom());
            existingAdmin.setPhoto(userSimple.getPhoto());
            userSimple.setEmail(userSimple.getEmail());
            userSimple.setVille(userSimple.getVille());
            userSimple.setPays(userSimple.getPays());
            // Set other fields as needed
            userSimpleRepository.save(existingAdmin);
            return new ReponseMessage("Utilisateur modifié avec succès", true);
        } else {
            return new ReponseMessage("Désolé, Utilisateur non trouvé", false);
        }
    }

    public void desactiverCompte(Long userId) {
        User user = userSimpleRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        user.setEtat(false); // Mettez à false pour désactiver le compte
        userSimpleRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userSimpleRepository.findByUsername(username);
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
    @Override
    public List<Object> NombreAmbassadeur() {
        return userSimpleRepository.NombreAmbassadeur();
    }

    @Override
    public List<Object> NombreUser() {
        return userSimpleRepository.NombreUser();
    }

    @Override
    public List<Object> NombreUserparpays(String pays) {
        return userSimpleRepository.NombreUserParPays(pays);
    }

    // Other methods...

}
