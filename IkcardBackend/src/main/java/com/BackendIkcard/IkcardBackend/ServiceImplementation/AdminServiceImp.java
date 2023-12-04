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

        if (adminnistrateurRepository.findById(administrateur.getId()) !=null) {
            return adminnistrateurRepository.findById(administrateur.getId())
                    .map(administrateur1->{
                        administrateur1.setNom(administrateur.getNom());
                        /*administrateur1.setPrenom(administrateur.getPrenom());
                        administrateur1.setAdresse(administrateur.getAdresse());
                        administrateur1.setVille(administrateur.getVille());*/
                        adminnistrateurRepository.save(administrateur);
                        ReponseMessage message = new ReponseMessage("administrateur modifié avec succes", true);
                        return  message;
                    }).orElseThrow(() -> new RuntimeException("Désole, administrateur non trouvée"));
        }else {
            ReponseMessage message = new ReponseMessage("Désole, administrateur non trouvée", false);

            return message;
        }
    }

    @Override
    public List<Administrateur> afficherToutLesAdministrateur() {
        return adminnistrateurRepository.findAll();
    }

    @Override
    public ReponseMessage SupprimerAdministrateur(Long id) {
            final  Administrateur administrateur = null;
            if (adminnistrateurRepository.findById(id) != null) {
                administrateur.setEtat(false);
                ReponseMessage message = new ReponseMessage("Administrateur supprimée avec succes", true);
                return message;
            }
            else {
                ReponseMessage message = new ReponseMessage("Administrateur non trouvée", false);
                return message;
            }

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
        return adminnistrateurRepository.findAll();
    }

    @Override
    public void deleteAdministrateurById(Long id) {

    }
}
