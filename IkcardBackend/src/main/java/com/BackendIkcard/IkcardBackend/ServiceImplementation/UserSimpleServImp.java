package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import com.BackendIkcard.IkcardBackend.Models.User;
import com.BackendIkcard.IkcardBackend.Models.UserSimple;
import com.BackendIkcard.IkcardBackend.Repository.UserSimpleRepositoty;
import com.BackendIkcard.IkcardBackend.Service.UserSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSimpleServImp implements UserSimpleService {


    private final UserSimpleRepositoty userSimpleRepositoty;

    @Autowired
    public UserSimpleServImp(UserSimpleRepositoty userSimpleRepositoty) {
        this.userSimpleRepositoty = userSimpleRepositoty;
    }


   @Override
    public ReponseMessage creerUserSimple(UserSimple userSimple) {
        if (userSimpleRepositoty.findByEmail(userSimple.getEmail()) == null) {
            userSimpleRepositoty.save(userSimple);
            ReponseMessage message = new ReponseMessage("Utilisateur ajouté avec succès", true);
            return message;
        } else {
            ReponseMessage message = new ReponseMessage("Cet utilisateur existe déjà", false);
            return message;
        }
    }

    @Override
    public User saveUserSimple(UserSimple user) {
        return userSimpleRepositoty.save(user);
    }

    @Override
    public ReponseMessage modifierUserSimple(UserSimple userSimple) {
        if (userSimpleRepositoty.findById(userSimple.getId()) != null) {
            return userSimpleRepositoty.findById(userSimple.getId())
                    .map(userSimple1 -> {
                        userSimple1.setNom(userSimple.getNom());
                        userSimple1.setPrenom(userSimple.getPrenom());
                        userSimple1.setAdresse(userSimple.getAdresse());
                        userSimple1.setVille(userSimple.getVille());
                        userSimpleRepositoty.save(userSimple1);
                        ReponseMessage message = new ReponseMessage("Utilisateur modifié avec succès", true);
                        return message;
                    }).orElseThrow(() -> new RuntimeException("Désolé, utilisateur non trouvée"));
        } else {
            ReponseMessage message = new ReponseMessage("Désolé, utilisateur non trouvée", false);
            return message;
        }
    }

    @Override
    public List<UserSimple> afficherToutLesUserSimple() {
        return userSimpleRepositoty.findAll();
    }

    @Override
    public ReponseMessage SupprimerUserSimple(Long id) {
        final Ambassadeur ambassadeur = null;
        if (userSimpleRepositoty.findById(id) != null) {
            ambassadeur.setEtat(false);
            ReponseMessage message = new ReponseMessage("Utilisateur supprimé avec succès", true);
            return message;
        } else {
            ReponseMessage message = new ReponseMessage("Utilisateur non trouvée", false);
            return message;
        }
    }

    @Override
    public int NombreUserSimple() {
        return 0; // You may want to implement this based on your business logic.
    }

    @Override
    public List<Object> NombreUserSimpleParVille() {
        return null; // You may want to implement this based on your business logic.
    }

    @Override
    public List<UserSimple> NouveauUserSimple() {
        return null; // You may want to implement this based on your business logic.
    }
}
