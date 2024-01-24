package com.BackendIkcard.IkcardBackend.Service;

import com.BackendIkcard.IkcardBackend.Models.QRCodeData;
import com.BackendIkcard.IkcardBackend.Models.Users;

import java.util.List;
import java.util.Optional;

public interface QRCodeService {

    byte[] generateQRCodeData(String data, int width, int height);


}

