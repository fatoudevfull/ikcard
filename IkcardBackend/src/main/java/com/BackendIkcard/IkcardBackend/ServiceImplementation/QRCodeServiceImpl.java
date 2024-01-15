package com.BackendIkcard.IkcardBackend.ServiceImplementation;


import com.BackendIkcard.IkcardBackend.Models.QRCodeData;
import com.BackendIkcard.IkcardBackend.Models.Users;
import com.BackendIkcard.IkcardBackend.Repository.QRCodeDataRepository;
import com.BackendIkcard.IkcardBackend.Service.QRCodeService;
import com.BackendIkcard.IkcardBackend.Service.UsersService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    @Autowired
    private UsersService userService;
    @Autowired
    private QRCodeDataRepository qrCodeDataRepository;


    // Méthode pour générer les données du QRCode avec les informations du profil de l'utilisateur
    private byte[] generateQRCodeDataI(Users user, int width, int height) {
        // Construire le contenu du QRCode avec les informations du profil de l'utilisateur
        String content = String.format("Nom: %s, Prénom: %s, Email: %s, Numéro: %s",
                user.getNom(), user.getPrenom(), user.getEmail(), user.getNumero());

        // Appel à une méthode de génération de QRCode (vous devez implémenter cela)
        return generateQRCode(content, width, height);
    }


public byte[] generateQRCodeData(Users user) {
    String userData = generateQRCodeDataInternal(user);
    int width = 300;
    int height = 300;
    return generateQRCode(userData, width, height);
}

    private String generateQRCodeDataInternal(Users user) {
        JSONObject userData = new JSONObject();
        userData.put("username", user.getUsername());
        userData.put("email", user.getEmail());
        userData.put("phoneNumber", user.getNumero());
        // Ajoutez d'autres informations au besoin

        return userData.toString();
    }

    private byte[] generateQRCode(String content, int width, int height) {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            return outputStream.toByteArray();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public void saveQRCodeData(String qrCode) {
        QRCodeData qrCodeData = new QRCodeData();
        qrCodeData.setQrCode(qrCode);
        qrCodeDataRepository.save(qrCodeData);
    }

    @Override
    public Optional<QRCodeData> getQRCodeData(Long id) {
        return qrCodeDataRepository.findById(id);
    }



    }



