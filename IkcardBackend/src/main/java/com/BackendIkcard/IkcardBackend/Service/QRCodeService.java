package com.BackendIkcard.IkcardBackend.Service;

import com.BackendIkcard.IkcardBackend.Models.QRCodeData;
import com.BackendIkcard.IkcardBackend.Models.Users;

import java.util.Optional;

public interface QRCodeService {
   // byte[] generateQRCode( Users users);
   // public byte[] generateQRCodeData(Users user, int width, int height);
    public void saveQRCodeData(String qrCode);
    Optional<QRCodeData> getQRCodeData(Long id);
    public byte[] generateQRCodeData(Users user);

}

