package com.BackendIkcard.IkcardBackend.Service;

import com.BackendIkcard.IkcardBackend.Models.Users;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<Users> getAllUsers();
    Optional<Users> findByUsername(String username);
}
