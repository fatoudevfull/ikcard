package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import com.BackendIkcard.IkcardBackend.Repository.AmbassadeurRepositoty;
import com.BackendIkcard.IkcardBackend.Service.AmbassadeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmbassadeurServImp implements AmbassadeurService {
    @Autowired
    private AmbassadeurRepositoty ambassadeurRepositoty;
    @Override
    public ReponseMessage creerAmbassadeur(Ambassadeur ambassadeur) {
        if (ambassadeurRepositoty.findByEmail(ambassadeur.getEmail()) == null){
            ambassadeurRepositoty.save(ambassadeur);
            ReponseMessage message = new ReponseMessage("Ambassadeur ajouté avec succes", true);
            return  message;
        }else {
            ReponseMessage message = new ReponseMessage("Cet Ambassadeur existe déjà ", false);

            return message;
        }
    }

    @Override
    public ReponseMessage modifierAmbassadeur(Ambassadeur ambassadeur) {
        if (ambassadeurRepositoty.findById(ambassadeur.getId()) !=null) {
            return ambassadeurRepositoty.findById(ambassadeur.getId())
                    .map(ambassadeur1->{
                        ambassadeur1.setNom(ambassadeur.getNom());
                        ambassadeur1.setPrenom(ambassadeur.getPrenom());
                        ambassadeur1.setAdresse(ambassadeur.getAdresse());
                        ambassadeur1.setVille(ambassadeur.getVille());
                        ambassadeurRepositoty.save(ambassadeur1);
                        ReponseMessage message = new ReponseMessage("Ambassadeur modifié avec succes", true);
                        return  message;
                    }).orElseThrow(() -> new RuntimeException("Désole, Ambassadeur non trouvée"));
        }else {
            ReponseMessage message = new ReponseMessage("Désole, Ambassadeur non trouvée", false);

            return message;
        }
    }

    @Override
    public List<Ambassadeur> afficherToutLesAmbassadeur() {
        return ambassadeurRepositoty.findAll();
    }

    @Override
    public ReponseMessage SupprimerAmbassadeur(Long id) {
        final Ambassadeur ambassadeur = null;
        if (ambassadeurRepositoty.findById(id) != null) {
            ambassadeur.setEtat(false);
            ReponseMessage message = new ReponseMessage("Ambassadeur supprimée avec succes", true);
            return message;
        }
        else {
            ReponseMessage message = new ReponseMessage("Ambassadeur non trouvée", false);
            return message;
        }
    }

    @Override
    public int NombreAmbassadeur() {
        return 0;
    }

    @Override
    public List<Object> NombreAmbassadeurParVille() {
        return null;
    }

    @Override
    public List<Ambassadeur> NouveauAmbassadeur() {
        return null;
    }
}
