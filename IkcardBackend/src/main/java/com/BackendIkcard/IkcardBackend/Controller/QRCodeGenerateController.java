package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Models.QRCodeData;
import com.BackendIkcard.IkcardBackend.Models.Users;
import com.BackendIkcard.IkcardBackend.Repository.QRCodeDataRepository;
import com.BackendIkcard.IkcardBackend.Repository.UsersRepository;
import com.BackendIkcard.IkcardBackend.Service.QRCodeService;
import com.BackendIkcard.IkcardBackend.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
@RequestMapping("/qr")
public class QRCodeGenerateController {

    @Autowired
    private QRCodeService qrCodeService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private QRCodeService qrCodeGeneratorService;
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private QRCodeDataRepository qrCodeDataRepository;



    @GetMapping(value = "/generate/{username}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQR(@PathVariable String username) {
        Optional<Users> userOptional = usersService.findByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Users user = userOptional.get();
        byte[] qrCode = qrCodeService.generateQRCodeData(user);

        return ResponseEntity.ok().body(qrCode);
    }





    @PostMapping("/scan")
    public ResponseEntity<String> scanQRCode(@RequestBody String scannedQRCode) {
        // Vous devez implémenter la logique pour extraire les informations du QR code scanné
        String userInfo = extractUserInfoFromQRCode(scannedQRCode);

        // Utilisez les informations de l'utilisateur comme nécessaire
        System.out.println(userInfo);

        return ResponseEntity.ok(userInfo);
    }

    private String extractUserInfoFromQRCode(String scannedQRCode) {
        // Vous devez implémenter la logique pour extraire les informations du QR code
        // Exemple fictif : Vous pouvez simplement renvoyer le contenu du QR Code
        return scannedQRCode;
    }


    @PostMapping("/save")
    public ResponseEntity<String> saveQRCodeData(@RequestBody String qrCode) {
        // Supposez que vous ayez une méthode dans votre service QRCodeService pour sauvegarder
        // les données du QR code dans la base de données, puis renvoyer l'ID généré.
        // Cette méthode peut également être utilisée pour obtenir les données du QR code par la suite.
        qrCodeService.saveQRCodeData(qrCode);
        // Vous pouvez renvoyer l'ID généré ou un message de succès ici.
        return ResponseEntity.ok("QR code saved successfully.");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<byte[]> getQRCodeData(@PathVariable Long id) {
        Optional<QRCodeData> qrCodeDataOptional = qrCodeService.getQRCodeData(id);

        if (qrCodeDataOptional.isPresent()) {
            String qrCode = qrCodeDataOptional.get().getQrCode();
            // Convertir le texte du QR code en tableau d'octets (byte[])
            byte[] qrCodeBytes = qrCode.getBytes(StandardCharsets.UTF_8);
            // Vous pouvez ajouter des en-têtes personnalisés si nécessaire
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentLength(qrCodeBytes.length);
            return new ResponseEntity<>(qrCodeBytes, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
