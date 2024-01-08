package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.UserSimple;
import com.BackendIkcard.IkcardBackend.Repository.UserSimpleRepository;
import com.BackendIkcard.IkcardBackend.Service.UserSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
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
            // Set the current date
            userSimple.setDateCreationCompte(new Date());
            return new ReponseMessage("Cet utilisateur existe déjà", false);
        }
    }




    @Override
    public UserSimple findById(Long userId) {
        return userSimpleRepository.findById(userId).orElse(null);
    }

    @Override
    public ReponseMessage modifierUserSimple(Long id, UserSimple userSimple) {
        Optional<UserSimple> existingUserOptional = userSimpleRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            UserSimple existingUser = existingUserOptional.get();

            // Set etat to true before updating
            userSimple.setEtat(true);

            // Vérifier chaque champ avant la mise à jour
            if (userSimple.getNom() != null) {
                existingUser.setNom(userSimple.getNom());
            }

            if (userSimple.getNumero() != null) {
                existingUser.setNumero(userSimple.getNumero());
            }

            if (userSimple.getAdresse() != null) {
                existingUser.setAdresse(userSimple.getAdresse());
            }

            if (userSimple.getPrenom() != null) {
                existingUser.setPrenom(userSimple.getPrenom());
            }

            if (userSimple.getPhotoProfil() != null) {
                existingUser.setPhotoProfil(userSimple.getPhotoProfil());
            }

            if (userSimple.getEmail() != null) {
                existingUser.setEmail(userSimple.getEmail());
            }

            if (userSimple.getVille() != null) {
                existingUser.setVille(userSimple.getVille());
            }

            if (userSimple.getPays() != null) {
                existingUser.setPays(userSimple.getPays());
            }

            // Set other fields as needed

            userSimpleRepository.save(existingUser);
            return new ReponseMessage("Utilisateur modifié avec succès", true);
        } else {
            return new ReponseMessage("Désolé, Utilisateur non trouvé", false);
        }
    }


    public void desactiverCompte(Long userId) {
        UserSimple user = userSimpleRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        user.setEtat(false); // Mettez à false pour désactiver le compte
        userSimpleRepository.save(user);
    }

    @Override
    public Optional<UserSimple> findByUsername(String username) {
        return userSimpleRepository.findByUsername(username);
    }

    public void activerCompte(Long userId) {
        UserSimple user = userSimpleRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        user.setEtat(true); // Mettez à true pour activer le compte
        userSimpleRepository.save(user);
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
