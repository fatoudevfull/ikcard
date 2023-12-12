package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Models.QRCodeData;
import com.BackendIkcard.IkcardBackend.Models.User;
import com.BackendIkcard.IkcardBackend.Repository.QRCodeDataRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class QRCodeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QRCodeDataRepository qrCodeDataRepository;

    public byte[] generateAndSaveQRCode(User user) throws WriterException, IOException {
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

        // Save the QR code data in the database
        QRCodeData qrCodeDataEntity = new QRCodeData();
        qrCodeDataEntity.setQrCode(qrCodeData);
        qrCodeDataRepository.save(qrCodeDataEntity);

        // Associate the QR code data with the user
        user.setQrCodeData(qrCodeDataEntity);

        // Save the user entity with the associated QR code data
        userRepository.save(user);
        return qrCodeData;
    }
}
