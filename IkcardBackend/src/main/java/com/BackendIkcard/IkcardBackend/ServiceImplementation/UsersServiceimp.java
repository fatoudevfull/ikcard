package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.Exeption.FileStorageException;
import com.BackendIkcard.IkcardBackend.Models.Entreprise;
import com.BackendIkcard.IkcardBackend.Models.Users;
import com.BackendIkcard.IkcardBackend.Repository.UsersRepository;
import com.BackendIkcard.IkcardBackend.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceimp implements UsersService {
    private UsersRepository userRepository;


    @Autowired
    public void UsersServiceImpl(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public void ajouterPhoto(Long userId, MultipartFile photo) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + userId));

        try {
            user.setPhotoData(photo.getBytes());
            user.setPhotoType(resolveImageType(photo));
            user.setPhotoProfil(photo.getOriginalFilename());
            userRepository.save(user);
        } catch (IOException e) {
            throw new FileStorageException("Erreur lors de l'enregistrement de la photo pour l'utilisateur avec l'ID : " + userId, e);
        }
    }

    @Override
    public byte[] getImagephotoProfil(Long id) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("la photo non trouvée avec l'ID : " + id));

        return users.getPhotoData();
    }



    private String resolveImageType(MultipartFile photo) {
        return MimeTypeUtils.parseMimeType(photo.getContentType()).getSubtype();
    }

}
