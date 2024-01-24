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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    @Autowired
    private UsersService userService;
    @Autowired
    private QRCodeDataRepository qrCodeDataRepository;


    // Méthode pour générer les données du QRCode avec les informations du profil de l'utilisateur


    @Override

    public byte[] generateQRCodeData(String data, int width, int height) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}



