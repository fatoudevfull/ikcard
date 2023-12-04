package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir; // C’est le répertoire dans lequel les fichiers seront sauvegardés

    public String saveFile(MultipartFile file, String imageName) throws IOException {
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        Path uploadPath = Paths.get(uploadDir);

        // Créez le répertoire s’il n’existe pas
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Enregistrez le fichier dans le répertoire spécifié
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        // Si vous avez besoin d’effectuer un traitement supplémentaire, tel que le redimensionnement des images, vous pouvez le faire ici

        // À des fins de démonstration, imprimons le chemin d’accès au fichier
        System.out.println("Fichier enregistré dans : " + filePath);

        return fileName;
    }

    public byte[] getFileBytes(String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        return Files.readAllBytes(filePath);
    }

    public void deleteFile(String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(fileName);

        // Supprimer le fichier
        FileSystemUtils.deleteRecursively(filePath.toFile());
    }

    private String generateUniqueFileName(String originalFileName) {
        // Vous pouvez implémenter une logique pour générer un nom de fichier unique ici
        // Pour simplifier, ajout d’un horodatage au nom de fichier d’origine
        return System.currentTimeMillis() + "_" + originalFileName;
    }
}
