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
        Optional<Users> users = usersService.getUsersByUsername(username);
        if (!users.isEmpty()) {
            Users user = users.get();
            byte[] qrCode = qrCodeService.generateQRCodeData(String.valueOf(user), 300, 300);

            // Inclure l'URL de la deuxième méthode dans la réponse
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/usersimple/get/" + username);
            return new ResponseEntity<>(qrCode, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    }








