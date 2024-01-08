package com.BackendIkcard.IkcardBackend.Service;

import com.BackendIkcard.IkcardBackend.Models.Users;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<Users> getAllUsers();
    Optional<Users> findByUsername(String username);
    void ajouterPhoto(Long userId, MultipartFile photo);

}
