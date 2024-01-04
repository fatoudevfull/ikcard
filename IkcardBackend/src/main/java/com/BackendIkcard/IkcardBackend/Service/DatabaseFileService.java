package com.BackendIkcard.IkcardBackend.Service;

import com.BackendIkcard.IkcardBackend.Models.DatabaseFile;
import org.springframework.web.multipart.MultipartFile;

public interface DatabaseFileService {

    public DatabaseFile storeFile(MultipartFile file);
    public DatabaseFile getFile(String fileId);
}
