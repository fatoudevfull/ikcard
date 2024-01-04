package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.Exeption.FileStorageException;
import com.BackendIkcard.IkcardBackend.Models.Annonce;
import com.BackendIkcard.IkcardBackend.Repository.AnnonceRepository;
import com.BackendIkcard.IkcardBackend.Service.AnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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
    public Annonce storeAnnonceWithFile(Annonce annonce, MultipartFile file) {
        try {
            // Normaliser le nom du fichier
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            // Vérifier si le nom du fichier contient des caractères invalides
            if(fileName.contains("..")) {
                throw new FileStorageException("Pardon! Le nom de fichier contient une séquence de chemin non valide " + fileName);
            }

            annonce.setFileName(fileName);
            annonce.setFileType(file.getContentType());
            annonce.setData(file.getBytes());

            return annonceRepository.save(annonce);
        } catch (IOException ex) {
            throw new FileStorageException("Impossible de stocker le fichier" + file.getOriginalFilename() + ". Veuillez réessayer!", ex);
        }
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
