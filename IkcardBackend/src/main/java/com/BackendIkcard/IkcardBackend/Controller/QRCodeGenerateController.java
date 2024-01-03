package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Models.Users;
import com.BackendIkcard.IkcardBackend.Service.QRCodeService;
import com.BackendIkcard.IkcardBackend.Service.UsersService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@RestController
@RequestMapping("/qr")
public class QRCodeGenerateController {

    @Autowired
    private QRCodeService qrCodeService;
    @Autowired
    private UsersService usersService;

    @GetMapping(value = "/generate/{username}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@PathVariable String username) {
        Optional<Users> userOptional = usersService.findByUsername(username);
        System.out.println(username);

        if (userOptional.isEmpty()) {
            // Gérer le cas où l'utilisateur n'est pas trouvé
            System.out.println("Le username n'existe pas");
            return ResponseEntity.notFound().build();
        }

        Users user = userOptional.get();
        System.out.println(username);
        System.out.println(user.getNom());
        System.out.println(username);
        System.out.println(user.getEmail());
        byte[] qrCode = qrCodeService.generateQRCode(user);

        // Vous pouvez ajouter des en-têtes personnalisés si nécessaire
        return ResponseEntity.ok().body(qrCode);
    }

}
