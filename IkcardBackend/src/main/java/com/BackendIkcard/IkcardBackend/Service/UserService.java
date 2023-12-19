package com.BackendIkcard.IkcardBackend.Service;

import com.BackendIkcard.IkcardBackend.Models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> findByUsername(String username);
}
