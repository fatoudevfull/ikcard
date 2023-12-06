package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import com.BackendIkcard.IkcardBackend.Repository.AmbassadeurRepository;
import com.BackendIkcard.IkcardBackend.Service.AmbassadeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmbassadeurServImp implements AmbassadeurService {
    @Autowired
    private AmbassadeurRepository ambassadeurRepositoty;


   /* @Override
    public ReponseMessage creerAmbassadeur(Ambassadeur ambassadeur) {
        // Implementation for creating an Ambassadeur
        return null;
    }*/

    @Override
    public Ambassadeur saveUser(Ambassadeur ambassadeur) {
        return ambassadeurRepositoty.save(ambassadeur);
    }

    @Override
    public ReponseMessage modifierAmbassadeur(Ambassadeur ambassadeur) {
        // Implementation for updating an Ambassadeur
        return null;
    }

    @Override
    public List<Ambassadeur> afficherToutLesAmbassadeur() {
        // Implementation for retrieving all Ambassadeurs
        return null;
    }

    @Override
    public ReponseMessage SupprimerAmbassadeur(Long id) {
        // Implementation for deleting an Ambassadeur
        return null;
    }

    @Override
    public int NombreAmbassadeur() {
        // Placeholder implementation; needs to be completed
        return 0;
    }

    @Override
    public List<Object> NombreAmbassadeurParVille() {
        // Placeholder implementation; needs to be completed
        return null;
    }

    @Override
    public List<Ambassadeur> NouveauAmbassadeur() {
        // Placeholder implementation; needs to be completed
        return null;
    }
}
