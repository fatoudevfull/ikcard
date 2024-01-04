package com.BackendIkcard.IkcardBackend.Service;

import com.BackendIkcard.IkcardBackend.Models.QRCodeData;
import com.BackendIkcard.IkcardBackend.Models.Users;

import java.util.Optional;

public interface QRCodeService {
    byte[] generateQRCode( Users users);
    public void saveQRCodeData(QRCodeData qr, byte[] qrCodeData);
    public void saveQRCodeData(String qrCode);
    Optional<QRCodeData> getQRCodeData(Long id);

    // Service pour la génération du QRCode

     //   byte[] generateQRCode(Users user);

}

