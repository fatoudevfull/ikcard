package com.BackendIkcard.IkcardBackend.ServiceImplementation;



import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Administrateur;
import com.BackendIkcard.IkcardBackend.Repository.AdminnistrateurRepository;
import com.BackendIkcard.IkcardBackend.Service.AdministrateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AdminServiceImp  implements AdministrateurService {
    @Autowired
    AdminnistrateurRepository adminnistrateurRepository;
    @Override
    public ReponseMessage creerAdministrateur(Administrateur administrateur) {
        if (adminnistrateurRepository.findByEmail(administrateur.getEmail()) == null){
            adminnistrateurRepository.save(administrateur);
            ReponseMessage message = new ReponseMessage("administrareur ajouté avec succes", true);
            return  message;
        }else {
            ReponseMessage message = new ReponseMessage("Cet administateur existe déjà ", false);

            return message;
        }
    }

    @Override
    public ReponseMessage modifierAdministrateur(Administrateur administrateur) {
        return null;
    }

    @Override
    public List<Administrateur> afficherToutLesAdministrateur() {
        return null;
    }

    @Override
    public ReponseMessage SupprimerAdministrateur(Long idAdministrateur) {
        return null;
    }

    @Override
    public Administrateur saveOrUpdateAdministrateur(Administrateur administrateur) {
        return null;
    }

    @Override
    public Optional<Administrateur> getAdministrateur(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Administrateur> getAdministrateur() {
        return null;
    }

    @Override
    public void deleteAdministrateurById(Long id) {

    }
}
