package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Models.Annonce;
import com.BackendIkcard.IkcardBackend.Repository.AnnonceRepository;
import com.BackendIkcard.IkcardBackend.Service.AnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AnnonceServImpl implements AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;

    @Override
    public Annonce saveAnnonceWithFile(Annonce annonce, MultipartFile file) {
        // Validate and handle file upload logic
        if (file != null && !file.isEmpty()) {
            // Set file-related attributes in the Annonce entity
            annonce.setFileName(file.getOriginalFilename());
            annonce.setFileType(file.getContentType());

            try {
                annonce.setData(file.getBytes());
            } catch (IOException e) {
                // Handle IOException
                e.printStackTrace();
            }
        }

        // Save the Annonce entity
        return annonceRepository.save(annonce);
    }

    @Override
    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

    @Override
    public Annonce getAnnonceById(Long id) {
        return annonceRepository.findById(id).orElse(null);
    }

    @Override
    public Annonce createAnnonce(Annonce annonce) {
        // Implement validation or business logic if needed
        return annonceRepository.save(annonce);
    }

    @Override
    public Annonce updateAnnonce(Long id, Annonce updatedAnnonce) {
        // Implement validation or business logic if needed
        Annonce existingAnnonce = annonceRepository.findById(id).orElse(null);
        if (existingAnnonce != null) {
            // Update existingAnnonce with fields from updatedAnnonce
            // Make sure to handle file-related attributes appropriately
            // ...

            // Save the updatedAnnonce
            return annonceRepository.save(existingAnnonce);
        }
        return null; // Or throw an exception indicating not found
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
