package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import com.BackendIkcard.IkcardBackend.Models.UserSimple;
import com.BackendIkcard.IkcardBackend.Repository.AmbassadeurRepositoty;
import com.BackendIkcard.IkcardBackend.Repository.UserSimpleRepositoty;
import com.BackendIkcard.IkcardBackend.Service.AmbassadeurService;
import com.BackendIkcard.IkcardBackend.Service.UserService;
import com.BackendIkcard.IkcardBackend.Service.UserSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSimpleServImp implements UserSimpleService {
    @Autowired
    private UserSimpleRepositoty userSimpleRepositoty;
    @Override
    public ReponseMessage creerUserSimple(UserSimple userSimple) {
        if (userSimpleRepositoty.findByEmail(userSimple.getEmail()) == null){
            userSimpleRepositoty.save(userSimple);
            ReponseMessage message = new ReponseMessage("Utilisateur ajouté avec succes", true);
            return  message;
        }else {
            ReponseMessage message = new ReponseMessage("Cet utilisateur existe déjà ", false);

            return message;
        }
    }

    @Override
    public ReponseMessage modifierUserSimple(UserSimple userSimple) {
        if (userSimpleRepositoty.findById(userSimple.getId()) !=null) {
            return userSimpleRepositoty.findById(userSimple.getId())
                    .map(userSimple1->{
                        userSimple1.setNom(userSimple.getNom());
                        userSimple1.setPrenom(userSimple.getPrenom());
                        userSimple1.setAdresse(userSimple.getAdresse());
                        userSimple1.setVille(userSimple.getVille());
                        userSimpleRepositoty.save(userSimple1);
                        ReponseMessage message = new ReponseMessage("utilisater modifié avec succes", true);
                        return  message;
                    }).orElseThrow(() -> new RuntimeException("Désole, utilisateur non trouvée"));
        }else {
            ReponseMessage message = new ReponseMessage("Désole, utilisateur non trouvée", false);

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
            ReponseMessage message = new ReponseMessage("Utilisateur supprimée avec succes", true);
            return message;
        }
        else {
            ReponseMessage message = new ReponseMessage("Utilisateur non trouvée", false);
            return message;
        }
    }

    @Override
    public int NombreUserSimple() {
        return 0;
    }

    @Override
    public List<Object> NombreUserSimpleParVille() {
        return null;
    }

    @Override
    public List<UserSimple> NouveauUserSimple() {
        return null;
    }




}
