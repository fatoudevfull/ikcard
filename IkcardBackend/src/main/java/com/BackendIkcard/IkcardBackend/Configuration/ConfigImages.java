package com.BackendIkcard.IkcardBackend.Configuration;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ConfigImages {

    public static String localhost = "http://127.0.0.1/";

    public static String userLocation = "C:/xampp/htdocs/ikcard/images/photo/";
    public static String entrepriseLocation = "C:/xampp/htdocs/ikcard/images/imageCouverture/";
    public static String carteLocation = "C:/xampp/htdocs/ikcard/images/photoProfil/";
    public static String carteLocation1 = "C:/xampp/htdocs/ikcard/images/imageCouverture/";
    public static String carteLocation2 = "C:/xampp/htdocs/ikcard/images/Catalogue/";
    public static String contactLocation = "C:/xampp/htdocs/ikcard/images/photoProfil/";
    public static String adminLocation = "C:/xampp/htdocs/ikcard/images/photo/";
    public static String ambassadeurLocation = "C:/xampp/htdocs/ikcard/images/photo/";
    public static String annonceLocation = "C:/xampp/htdocs/ikcard/images/photo/";

    public static String ConfigImages(String typeImage, MultipartFile file, String fileName) {
        String src = "";
        String location = "";

        switch (typeImage) {
            case "user":
                location = userLocation;
                break;
            case "carte":
                location = carteLocation;
                break;
            case "entreprise":
                location = entrepriseLocation;
                break;
            case "admin":
                location = adminLocation;
                break;
            case "ambassadeur":
                location = ambassadeurLocation;
                break;
            case "cart":
                location = carteLocation1;
                break;
            case "carte1":
                location = carteLocation2;
                break;
            case "annonce":
                location = annonceLocation;
                break;
            default:
                location = contactLocation;
                break;
        }

        try {
            Path filePath = Paths.get(location + fileName);

            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                Files.copy(file.getInputStream(), filePath);
                src = location + fileName;
            } else {
                Files.delete(filePath);
                Files.copy(file.getInputStream(), filePath);
                src = location + fileName;
            }
        } catch (IOException e) {
            e.printStackTrace();
            src = null;
        }


        return src;
    }

    public static String saveImage(String typeImage, String fileContent, String fileName) {
        String src = "";
        String location = "";

        switch (typeImage) {
            // ... other cases ...

            case "annonce":
                location = annonceLocation;
                break;

            // ... handle other cases ...

            default:
                location = contactLocation;
                break;
        }

        try {
            Path filePath = Paths.get(location + fileName);

            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, fileContent.getBytes());
                src = location + fileName;
            } else {
                Files.delete(filePath);
                Files.write(filePath, fileContent.getBytes());
                src = location + fileName;
            }
        } catch (IOException e) {
            e.printStackTrace();
            src = null;
        }

        return src;
    }


    private static String getLocation(String typeImage) {
        // Logique pour obtenir le chemin de sauvegarde en fonction du type d'image
        return typeImage;
    }

    private static String getServer(String typeImage) {
        // Logique pour obtenir l'URL du serveur en fonction du type d'image
        return typeImage;
    }

}
