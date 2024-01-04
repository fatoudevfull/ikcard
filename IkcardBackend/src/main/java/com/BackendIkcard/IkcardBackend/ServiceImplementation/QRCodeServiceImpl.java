package com.BackendIkcard.IkcardBackend.ServiceImplementation;


import com.BackendIkcard.IkcardBackend.Models.QRCodeData;
import com.BackendIkcard.IkcardBackend.Models.Users;
import com.BackendIkcard.IkcardBackend.Repository.QRCodeDataRepository;
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
import java.util.Optional;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    @Autowired
    private UsersService userService;
    @Autowired
    private QRCodeDataRepository qrCodeDataRepository;

    // Méthode pour générer les données du QRCode
/*    @Override
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
    }*/

    // Méthode pour générer les données du QRCode avec les informations du profil de l'utilisateur
    private byte[] generateQRCodeDataI(Users user, int width, int height) {
        // Construire le contenu du QRCode avec les informations du profil de l'utilisateur
        String content = String.format("Nom: %s, Prénom: %s, Email: %s, Numéro: %s",
                user.getNom(), user.getPrenom(), user.getEmail(), user.getNumero());

        // Appel à une méthode de génération de QRCode (vous devez implémenter cela)
        return generateQRCode(content, width, height);
    }

    // Dans votre service QRCode
    public byte[] generateQR(Users user) {
        int width = 300;
        int height = 300;

        // Construire le contenu du QR Code avec les informations de l'utilisateur
        String content = String.format("Nom: %s, Prenom: %s, Email: %s, Numero: %s",
                user.getNom(), user.getPrenom(), user.getEmail(), user.getNumero());

        // Appeler la méthode de génération de QR Code avec la taille spécifiée
        return generateQRCode(content, width, height);
    }

    @Override
    public byte[] generateQRCode(Users user) {
        int width = 300;
        int height = 300;
        return generateQRCodeData(user, width, height);
    }

    private byte[] generateQRCodeData(Users user, int width, int height) {
        // Construire le contenu du QR code avec les informations de l'utilisateur
        String content = String.format("Nom: %s, Prénom: %s, Email: %s, Numéro: %s",
                user.getNom(), user.getPrenom(), user.getEmail(), user.getNumero());

        return generateQRCode(content, width, height);
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

    @Override
    public void saveQRCodeData(QRCodeData qr, byte[] qrCodeData) {
        QRCodeData qrCodeDataEntity = new QRCodeData();
        qrCodeDataEntity.setQrCode(String.valueOf(qr));
        // Enregistrez les données du QR code dans la base de données
        qrCodeDataRepository.save(qrCodeDataEntity);
        // Associez les données du QR code à l'utilisateur
        qr.setQrCode(String.valueOf(qrCodeDataEntity));
        // Enregistrez l'entité utilisateur mise à jour dans la base de données
        // (Assurez-vous que votre service utilisateur a une méthode pour cela)
        qrCodeDataRepository.save(qr);
    }
    // Implémentation du service pour la génération du QRCode
/*        @Override
        public byte[] generateQRCode(Users user) {
            int width = 300;
            int height = 300;
            String content = String.format("Nom: %s, Prénom: %s, Email: %s, Numéro: %s",
                    user.getNom(), user.getPrenom(), user.getEmail(), user.getNumero());
            return generateQRCodeData(content, width, height);
        }

        private byte[] generateQRCodeData(String content, int width, int height) {
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
        }*/
    }



