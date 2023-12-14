package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.*;
import com.BackendIkcard.IkcardBackend.Repository.CarteRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserRepository;
import com.BackendIkcard.IkcardBackend.Service.CarteService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class CarteServIpml implements CarteService {
   // private CarteRepository carteRepository;
   private final CarteRepository carteRepository;
   @Autowired
   private  UserRepository userRepository;


    // Dans votre CarteService
    public CarteServIpml(CarteRepository carteRepository) {
        this.carteRepository = carteRepository;
    }

/*    public Carte creerCarte(Carte carte, Long id) {
        // Vérifier si l'utilisateur a déjà une carte
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Vérifier si l'utilisateur a déjà une carte
            if (user.getCarte() != null) {
                // L'utilisateur a déjà une carte, vous pouvez choisir de lever une exception,
                // de mettre à jour la carte existante, ou de prendre une autre action appropriée.
                // Dans cet exemple, je lève une exception.
                throw new RuntimeException("L'utilisateur a déjà une carte.");
            }

            // L'utilisateur n'a pas encore de carte, associez la nouvelle carte à l'utilisateur
            carte.setUser(user);

            // Définir la date de création de la carte
            carte.setDateCreationCarte(new Date());

            // Sauvegarder la nouvelle carte
            return carteRepository.save(carte);
        } else {
            // L'utilisateur n'a pas été trouvé, vous pouvez choisir de lever une exception
            // ou de prendre une autre action appropriée. Dans cet exemple, je lève une exception.
            throw new RuntimeException("Utilisateur non trouvé avec l'ID : " + id);
        }
    }*/

public Carte creerCarte(Carte carte, String username) {
    // Vérifier si l'utilisateur existe dans la base de données
    Optional<User> userOptional = userRepository.findByUsername(username);

    if (userOptional.isPresent()) {
        User user = userOptional.get();

        // Vérifier si l'utilisateur a déjà une carte
        if (user.getCarte() != null) {
            // L'utilisateur a déjà une carte, vous pouvez choisir de lever une exception,
            // de mettre à jour la carte existante, ou de prendre une autre action appropriée.
            // Dans cet exemple, je lève une exception.
            throw new RuntimeException("L'utilisateur a déjà une carte.");
        }

        // L'utilisateur n'a pas encore de carte, associez la nouvelle carte à l'utilisateur
        carte.setUser(user);

        // Définir la date de création de la carte
        carte.setDateCreationCarte(new Date());

        // Sauvegarder la nouvelle carte
        return carteRepository.save(carte);
    } else {
        // L'utilisateur n'a pas été trouvé, vous pouvez choisir de lever une exception
        // ou de prendre une autre action appropriée. Dans cet exemple, je lève une exception.
        throw new RuntimeException("Utilisateur non trouvé avec le nom d'utilisateur : " + username);
    }
}


    public Carte enregistrerCarte(Carte carte, String username) {
        // Vérifier si l'utilisateur existe dans la base de données
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Vérifier si l'utilisateur a déjà une carte
            if (user.getCarte() != null) {
                // L'utilisateur a déjà une carte, vous pouvez choisir de lever une exception,
                // de mettre à jour la carte existante, ou de prendre une autre action appropriée.
                // Dans cet exemple, je lève une exception.
                throw new RuntimeException("L'utilisateur a déjà une carte.");
            }

            // L'utilisateur n'a pas encore de carte, associez la nouvelle carte à l'utilisateur
            carte.setUser(user);

            // Définir la date de création de la carte
            carte.setDateCreationCarte(new Date());

            // Sauvegarder la nouvelle carte
            return carteRepository.save(carte);
        } else {
            // L'utilisateur n'a pas été trouvé, vous pouvez choisir de lever une exception
            // ou de prendre une autre action appropriée. Dans cet exemple, je lève une exception.
            throw new RuntimeException("Utilisateur non trouvé avec le nom d'utilisateur : " + username);
        }
        //return carteRepository.save(carte);
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

  public String generateContentForUser(User user) {
        // Logic to generate content for the QR code based on user information
        // For example, concatenate user details like name, email, etc.
      String content = generateContentForUser(user); // Assuming 'this.user' is the user associated with the Carte
        String filePath = "path/to/your/qrcode/image.png";

        try {
            String savedFilePath = QrCodeGenerator.generateQrCode(content, filePath);
            this.setQrCode(savedFilePath);
        } catch (WriterException | IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return user.getNom() + "\n" + user.getEmail() + "\n" + user.getUsername();

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


}
