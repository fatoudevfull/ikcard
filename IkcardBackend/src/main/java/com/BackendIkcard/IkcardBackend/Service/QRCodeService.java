package com.BackendIkcard.IkcardBackend.Service;

import com.BackendIkcard.IkcardBackend.Models.Users;

public interface QRCodeService {
    byte[] generateQRCode( Users users);
}

