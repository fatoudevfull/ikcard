package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Models.QRCodeData;
import com.BackendIkcard.IkcardBackend.Models.Users;
import com.BackendIkcard.IkcardBackend.Models.UserSimple;
import com.BackendIkcard.IkcardBackend.Repository.QRCodeDataRepository;
import com.BackendIkcard.IkcardBackend.Repository.UsersRepository;
import com.BackendIkcard.IkcardBackend.Service.CarteService;
import com.BackendIkcard.IkcardBackend.Service.UsersService;
import com.BackendIkcard.IkcardBackend.ServiceImplementation.QRCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "qrcode", description = "Les actions reslisables par les code QR du systeme.")
@RestController
@RequestMapping("/qrcode")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeGeneratorService;
    @Autowired
    private CarteService carteService; // Assuming you have a CarteService

    @Autowired
    private UsersService userService; // Inject your user service
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private QRCodeDataRepository qrCodeDataRepository;


    @GetMapping(value = "/generatecode/{username}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateAndSaveQRCode(UserSimple user) throws WriterException, IOException {
        // Vérifier si l'utilisateur est null
        if (user == null) {
            throw new IllegalArgumentException("L'utilisateur ne peut pas être null");
        }

        int width = 300;
        int height = 300;

        String content = String.format("UserId: %d, Username: %s, Email: %s", user.getId(), user.getUsername(), user.getEmail());

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        byte[] qrCodeData = outputStream.toByteArray();
        String encodedQRCode = Base64.getEncoder().encodeToString(qrCodeData);

        // Enregistrer les données du code QR dans la base de données
        QRCodeData qrCodeDataEntity = new QRCodeData();
        qrCodeDataEntity.setQrCode(encodedQRCode);
        qrCodeDataRepository.save(qrCodeDataEntity);

        // Associer les données du code QR à l'utilisateur
        user.setQrCodeData(qrCodeDataEntity);
        System.out.println("email: "+user.getEmail());
        System.out.println("nom: "+user.getNom());
        System.out.println(user.getUsername());
        System.out.println("email: "+user.getNumero());
        System.out.println("nom: "+user.getPrenom());
        System.out.println("username: "+user.getUsername());

        // Enregistrer l'entité utilisateur avec les données du code QR associées
        userRepository.save(user);

        return qrCodeData;
    }




}


