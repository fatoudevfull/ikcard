package com.BackendIkcard.IkcardBackend.ServiceImplementation;


import com.BackendIkcard.IkcardBackend.Models.Users;
import com.BackendIkcard.IkcardBackend.Service.QRCodeService;
import com.BackendIkcard.IkcardBackend.Service.UsersService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    @Autowired
    private UsersService userService;
    // Méthode pour générer les données du QRCode
    @Override
    public byte[] generateQRCode(Users user) {
        int width = 300;
        int height = 300;
        return generateQRCodeData(user, width, height);
    }

    // Méthode pour générer les données du QRCode avec la taille spécifiée
    private byte[] generateQRCodeData(Users user, int width, int height) {
        String content = "Username: " + user.getUsername();
        // Ajoutez d'autres informations de l'utilisateur au contenu si nécessaire
        return generateQRCode(content, width, height);
    }

    // Méthode pour générer le QRCode en utilisant ZXing avec la taille spécifiée
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
            // Gérer l'exception (vous pouvez personnaliser cela en fonction de vos besoins)
            return new byte[0];
        }
    }




}
