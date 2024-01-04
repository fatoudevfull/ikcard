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
public class DatabaseFileServiceImplementation implements DatabaseFileService {

    @Autowired
    private DatabaseFileRepository dbFileRepository;

    @Autowired
    private EntrepriseRepository entrepriseRepository;
    @Override
    public DatabaseFile storeFile(MultipartFile file) {
        // Normaliser le nom du fichier
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Vérifiez si le nom du fichier contient des caractères invalides
            if(fileName.contains("..")) {
                throw new FileStorageException("Pardon! Le nom de fichier contient une séquence de chemin non valide " + fileName);
            }

            DatabaseFile dbFile = new DatabaseFile(fileName, file.getContentType(), file.getBytes());
            // dbFile.setUtilisateus(utilisateusRepository.findById(idDossier).get());
            //dbFile.setDossier(dossierRepository.findByIddossier(id));
            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Impossible de stocker le fichier" + fileName + ". Veuillez réessayer!", ex);
        }
    }

    @Override
    public DatabaseFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("Fichier introuvable avec l'identifiant" + fileId));
    }
}
