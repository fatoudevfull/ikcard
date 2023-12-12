package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Models.Carte;
import com.BackendIkcard.IkcardBackend.Models.User;
import com.BackendIkcard.IkcardBackend.Service.CarteService;
import com.BackendIkcard.IkcardBackend.Service.UserService;
import com.BackendIkcard.IkcardBackend.ServiceImplementation.QRCodeService;
import com.google.zxing.WriterException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "qrcode", description = "Les actions reslisables par les code QR du systeme.")
@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeGeneratorService;
    @Autowired
    private CarteService carteService; // Assuming you have a CarteService

    @Autowired
    private UserService userService; // Inject your user service

    @GetMapping(value = "/generate/{userId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@PathVariable Long userId) {
        try {
            // Retrieve user information
            User user = userService.findById(userId);

            // Generate QR code based on user information
            byte[] qrCode = qrCodeGeneratorService.generateAndSaveQRCode(user);

            return ResponseEntity.ok().body(qrCode);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/generateBy/{userId}")
    public ResponseEntity<String> generateQrCodeForUser(@PathVariable Long userId) {
        try {
            Optional<User> userOptional = Optional.ofNullable(userService.findById(userId));

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String carte = carteService.generateContentForUser(user);
                return ResponseEntity.ok("Code QR généré et stocké pour l’utilisateur avec ID: " + userId);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable avec ID: " + userId);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Echec de la génération du code QR.");
        }
    }
}


//http://localhost:8080/api/qrcode/generate/JohnDoe