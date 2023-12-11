package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Configuration.ConfigImages;
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
    @Autowired
    private ImageService imageService;


    @Override
    public Annonce saveAnnonce(Annonce annonce) {
        if (annonce.getImage() != null) {
            String imageFileContent = annonce.getImage();
            String imageFileName = ConfigImages.saveImage("annonce", imageFileContent, String.valueOf(annonce.getId()));
            annonce.setImageFileName(imageFileName);
        }
        return annonceRepository.save(annonce);
    }






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


}
