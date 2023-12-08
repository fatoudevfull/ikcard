package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Models.Annonce;
import com.BackendIkcard.IkcardBackend.Repository.AnnonceRepository;
import com.BackendIkcard.IkcardBackend.Service.AnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AnnonceServImpl implements AnnonceService {

    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    private AnnonceRepository annonceRepository;

   @Override
    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }
    @Override
    public Optional<Annonce> getAnnonceById(Long id) {
        return annonceRepository.findById(id);
    }

    @Override
    public Annonce createAnnonce(Annonce annonce) {
        return annonceRepository.save(annonce);
    }

    @Override
    public Annonce updateAnnonce(Long id, Annonce updatedAnnonce) {
        if (annonceRepository.existsById(id)) {
            updatedAnnonce.setId(id);
            return annonceRepository.save(updatedAnnonce);
        }
        return null; // Handle not found case
    }

/*    @Override
    public void activerAnonce(Long id) {
        Optional<Annonce> existingAdmin = annonceRepository.findById(id);
        existingAdmin.ifPresent(annonce -> {
            // Set etat to true
            annonce.setEtat(true);

            // Save the updated entity
            annonceRepository.save(annonce);
        });
    }*/
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

    @Override
    public void deleteAnnonce(Long id) {
        annonceRepository.deleteById(id);
    }

    @Override
    public Annonce createAnnonceWithImage(String titre, String contenu, MultipartFile image, Long administrateurId) {
        try {
            String imageName = StringUtils.cleanPath(image.getOriginalFilename());
            Annonce annonce = new Annonce();
            annonce.setTitre(titre);
            annonce.setContenu(contenu);
            annonce.setImage(imageName);

            // Set other properties and relationships...

            // Save the image to the file system or cloud storage

            fileStorageService.saveFile(image, imageName);

            return annonceRepository.save(annonce);
        } catch (IOException e) {
            // Handle or log the IOException
            e.printStackTrace(); // Change this to proper logging
            return null; // Indicate failure
        }
    }
}
