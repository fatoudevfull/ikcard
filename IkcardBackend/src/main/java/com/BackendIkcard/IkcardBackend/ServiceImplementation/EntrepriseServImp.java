package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Entreprise;
import com.BackendIkcard.IkcardBackend.Repository.EntrepriseRepository;
import com.BackendIkcard.IkcardBackend.Service.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EntrepriseServImp implements EntrepriseService {
    @Autowired
    EntrepriseRepository entrepriseRepository;

    @Override
    public ReponseMessage creerEntreprise(Entreprise entreprise) {
        if (entrepriseRepository.findByNom(entreprise.getNom()) == null) {
            entrepriseRepository.save(entreprise);
            ReponseMessage message = new ReponseMessage("Entreprise ajouté avec succes", true);
            return message;
        } else {
            ReponseMessage message = new ReponseMessage("Cet entreprise existe déjà ", false);

            return message;
        }
    }

    @Override
    public ReponseMessage modifierEntreprise(Entreprise entreprise) {
        return null;
    }

    @Override
    public List<Entreprise> afficherToutLesEntreprise() {
        return entrepriseRepository.findAll();
    }

    @Override
    public void activerEntreprise(Long id) {
            Entreprise entreprise = entrepriseRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Médecin introuvable"));
            entreprise.setEtat(true);
            entrepriseRepository.save(entreprise);

    }

    @Override
    public Entreprise saveEntreprise(Entreprise entreprise) {
        // TODO Auto-generated method stub
        return entrepriseRepository.save(entreprise);
    }
    @Override
    public ReponseMessage SupprimerEntreprise(Long idEntreprise) {
        final Entreprise entreprise = null;
        if (entrepriseRepository.findById(idEntreprise) != null) {
            entreprise.setEtat(false);
            ReponseMessage message = new ReponseMessage("Entreprise supprimée avec succes", true);
            return message;
        } else {
            ReponseMessage message = new ReponseMessage("Entreprise non trouvée", false);
            return message;
        }


    }

    @Override
    public Entreprise saveOrUpdateEntreprise(Entreprise entreprise) {
        return null;
    }

    @Override
    public Optional<Entreprise> getEntrepriseById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Entreprise> getAllEntreprise() {
        return null;
    }
}
