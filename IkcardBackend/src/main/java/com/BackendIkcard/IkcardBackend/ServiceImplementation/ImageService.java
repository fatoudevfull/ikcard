package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${app.upload-dir}")
    private String uploadDir;  // Chemin vers le répertoire où les images seront stockées

    public String saveImage(MultipartFile file) throws IOException {
        // Générer un nom de fichier unique
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Construire le chemin complet vers le répertoire de stockage
        Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();

        // S'assurer que le répertoire de stockage existe, sinon le créer
        Files.createDirectories(uploadPath);

        // Construire le chemin complet vers le fichier
        Path filePath = uploadPath.resolve(fileName).normalize();

        // Copier le fichier vers le répertoire de stockage
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}
