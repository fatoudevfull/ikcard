package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Annonce;
import com.BackendIkcard.IkcardBackend.Repository.AnnonceRepository;
import com.BackendIkcard.IkcardBackend.Service.AnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AnnonceServImpl implements AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;



    @Override
    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

    @Override
    public void ajouterImageAnnonce(Long id, MultipartFile image) {
        Annonce annonce = annonceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Annonce non trouvée avec l'ID : " + id));

        try {
            annonce.setFileType(image.getContentType());
            annonce.setFileName(image.getOriginalFilename());
            annonce.setData(image.getBytes());

            annonceRepository.save(annonce);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'ajout de l'image à l'annonce.", e);
        }
    }

    @Override
    public byte[] getImage(Long id) {
        Annonce annonce = annonceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("annonce non trouvée avec l'ID : " + id));

        return annonce.getData();
    }

    @Override
  /*  public Annonce getAnnonceById(Long id) {
        return annonceRepository.findById(id).orElse(null);
    }*/

    public Annonce getAnnonceById(Long id) {
        Optional<Annonce> annonceOptional = annonceRepository.findById(id);

        if (annonceOptional.isPresent()) {
            Annonce annonce = annonceOptional.get();

            // Ne renvoyer que les informations non sensibles
            Annonce annonceFiltered = new Annonce();
            annonceFiltered.setId(annonce.getId());
            annonceFiltered.setTitre(annonce.getTitre());
            // Ajoutez d'autres champs non sensibles ici
            annonceFiltered.setDateAnnonce(annonce.getDateAnnonce());
            annonceFiltered.setFileName(annonce.getFileName());

            return annonceFiltered;
        } else {
            return null;
        }
    }


    @Override
    public Annonce createAnnonce(Annonce annonce) {
        // Implement validation or business logic if needed
        return annonceRepository.save(annonce);
    }

    @Override
    public ReponseMessage updateAnnonce(Long id, Annonce annonce) {
        // Implement validation or business logic if needed
        Optional<Annonce> existingAnnonceOptional = annonceRepository.findById(id);

        if (existingAnnonceOptional.isPresent()) {
            Annonce existingAnnonce = existingAnnonceOptional.get();

            // Set etat to true before updating
            existingAnnonce.setEtat(true);

            // Update only non-null properties
            if (annonce.getTitre() != null) {
                existingAnnonce.setTitre(annonce.getTitre());
            }
            if (annonce.getContenu() != null) {
                existingAnnonce.setContenu(annonce.getContenu());
            }
            if (annonce.getFileType() != null) {
                existingAnnonce.setFileType(annonce.getFileType());
            }
            if (annonce.getFileName() != null) {
                existingAnnonce.setFileName(annonce.getFileName());
            }
            // Set
            annonceRepository.save(existingAnnonce);
            return new ReponseMessage("annonce modifié avec succès", true);
        }
        return new ReponseMessage("Désolé, l'annoce non trouvé", false);
    }

    @Override
    public void deleteAnnonce(Long id) {
        // Implement validation or business logic if needed
        annonceRepository.deleteById(id);
    }

public void desactiverCompte(Long userId) {
    Annonce annonce = annonceRepository.findById(userId)
            .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

    annonce.setEtat(false); // Mettez à false pour désactiver le compte
    annonceRepository.save(annonce);
}

    public void activerCompte(Long userId) {
        Annonce annonce = annonceRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        annonce.setEtat(true); // Mettez à true pour activer le compte
        annonceRepository.save(annonce);
    }




}
