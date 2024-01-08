package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.Exeption.FileStorageException;
import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Carte;
import com.BackendIkcard.IkcardBackend.Models.UserSimple;
import com.BackendIkcard.IkcardBackend.Models.Users;
import com.BackendIkcard.IkcardBackend.Repository.CarteRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserSimpleRepository;
import com.BackendIkcard.IkcardBackend.Service.CarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CarteServIpml implements CarteService {
    // private CarteRepository carteRepository;
    private final CarteRepository carteRepository;
    @Autowired
    private UserSimpleRepository userRepository;


    // Dans votre CarteService
    public CarteServIpml(CarteRepository carteRepository) {
        this.carteRepository = carteRepository;
    }

    @Override
    public Carte creerCarteAvecNomPrenom(Carte carte, String username) {
        if (username == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le nom d'utilisateur ne peut pas être null");
        }

        Optional<UserSimple> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateure peut pas être null");
        }

        Users user = userOptional.get();
        carte.setUser(user);

        carte.setEtat(true);
        carte.setDateCreationCarte(new Date());
        carte.setNomComplet(user.getPrenom() + " " + user.getNom());

        return carteRepository.save(carte);
    }




    // Dans votre CarteService
    public ReponseMessage modifierCarte(long carteId, Carte cartetModifie) {
        Carte carteExistant = carteRepository.findById(carteId)
                .orElseThrow(() -> new NoSuchElementException("Carte introuvable"));

        // Mettez à jour les champs du contact existant avec les nouvelles valeurs
        carteExistant.setNomComplet(cartetModifie.getNomComplet());
        carteExistant.setEmail1(cartetModifie.getEmail1());
        carteExistant.setFixe1(cartetModifie.getFixe1());
        carteExistant.setAddresse(cartetModifie.getAddresse());
        carteExistant.setPosteOccupe(cartetModifie.getPosteOccupe());
        carteExistant.setFacebookLink(cartetModifie.getFacebookLink());
        carteExistant.setEmail2(cartetModifie.getEmail2());
        carteExistant.setCatalogue(cartetModifie.getCatalogue());
        carteExistant.setMobile3(cartetModifie.getMobile3());
        carteExistant.setWhatsappLink(cartetModifie.getWhatsappLink());
        carteExistant.setProfession(cartetModifie.getProfession());
        carteExistant.setWebSite(cartetModifie.getWebSite());
        carteExistant.setPhotoProfil(cartetModifie.getPhotoProfil());
        carteExistant.setPhotoCouverture(cartetModifie.getPhotoCouverture());
        carteExistant.setInstagramLink(cartetModifie.getInstagramLink());
        carteExistant.setLinkedinLink(cartetModifie.getLinkedinLink());
        // Ajoutez d'autres champs à mettre à jour

        carteRepository.save(carteExistant);

        return new ReponseMessage("Carte modifié avec succès", true);
    }

    //afficher tous les cartes
    public List<Carte> getAllCartes() {
        return carteRepository.findAll();
    }

    public void desactiverCompte(Long userId) {
        Carte carte = carteRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        carte.setEtat(false); // Mettez à false pour désactiver le compte
        carteRepository.save(carte);
    }

    public void activerCompte(Long userId) {
        Carte carte = carteRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        carte.setEtat(true); // Mettez à true pour activer le compte
        carteRepository.save(carte);
    }

    private void setQrCode(String savedFilePath) {
    }

    // Dans votre CarteService
    public Optional<Carte> afficherCarteParId(Long id) {
        return carteRepository.findById(id);
    }

    // Dans votre CarteService
    public void supprimerCarte(Long id) {
        // Assurez-vous que la carte existe avant de la supprimer
        if (carteRepository.existsById(id)) {
            carteRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Carte introuvable");
        }
    }

    @Override
    public List<Object> ListeCarteparEntreprise(String compagnie) {
        return carteRepository.ListeCarteparEntreprise(compagnie);
    }

    @Override
    public void ajouterphotoCouverture(Long userId, MultipartFile photo) {
        Carte carte = carteRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + userId));

        try {
            carte.setPhotoCouvertureData(photo.getBytes());
            carte.setPhotoCouvertureType(photo.getContentType());
            carte.setPhotoCouverture(photo.getOriginalFilename());
            carteRepository.save(carte);
        } catch (IOException e) {
            throw new FileStorageException("Erreur lors de l'enregistrement de la photo pour l'utilisateur avec l'ID : " + userId, e);
        }
    }


}
