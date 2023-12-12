package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.*;
import com.BackendIkcard.IkcardBackend.Repository.CarteRepository;
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


    // Dans votre CarteService


    public CarteServIpml(CarteRepository carteRepository) {
        this.carteRepository = carteRepository;
    }

    public Carte creerCarte(Carte carte) {
        // Votre logique de création de carte ici
        // Set the current date
        carte.setDateCreationCarte(new Date());
        return carteRepository.save(carte);
    }

    // Dans votre CarteService

    public ReponseMessage modifierCarte(long carteId, Carte cartetModifie) {
        Carte carteExistant = carteRepository.findById(carteId)
                .orElseThrow(() -> new NoSuchElementException("Carte introuvable"));

        // Mettez à jour les champs du contact existant avec les nouvelles valeurs
        carteExistant.setNomComplet(cartetModifie.getNomComplet());
        carteExistant.setEmail(cartetModifie.getEmail());
        carteExistant.setFixe1(cartetModifie.getFixe1());
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

  /*  public void generateAndStoreQrCodeForUser() {
        String content = generateContentForUser(user); // Assuming 'this.user' is the user associated with the Carte
        String filePath = "path/to/your/qrcode/image.png";

        try {
            String savedFilePath = QrCodeGenerator.generateQrCode(content, filePath);
            this.setQrCode(savedFilePath);
        } catch (WriterException | IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }*/
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


}
