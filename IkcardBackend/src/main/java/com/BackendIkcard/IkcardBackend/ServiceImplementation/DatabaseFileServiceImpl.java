package com.BackendIkcard.IkcardBackend.ServiceImplementation;



import com.BackendIkcard.IkcardBackend.Message.Exeption.FileNotFoundException;
import com.BackendIkcard.IkcardBackend.Message.Exeption.FileStorageException;
import com.BackendIkcard.IkcardBackend.Models.DatabaseFile;
import com.BackendIkcard.IkcardBackend.Repository.DatabaseFileRepository;
import com.BackendIkcard.IkcardBackend.Repository.EntrepriseRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserSimpleRepository;
import com.BackendIkcard.IkcardBackend.Repository.UsersRepository;
import com.BackendIkcard.IkcardBackend.Service.DatabaseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class DatabaseFileServiceImpl implements DatabaseFileService {

    @Autowired
    private DatabaseFileRepository dbFileRepository;

    @Autowired
    private EntrepriseRepository dossierRepository;
    @Autowired
    private UsersRepository utilisateusRepository;


    @Override
    public DatabaseFile storeFile(MultipartFile file, Long idUser, Long id) {
        return null;
    }

    @Override
    public DatabaseFile saveFile(MultipartFile file, Long idUser) {
        return null;
    }

    @Override
    public DatabaseFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("Fichier introuvable avec l'identifiant" + fileId));
    }
}
