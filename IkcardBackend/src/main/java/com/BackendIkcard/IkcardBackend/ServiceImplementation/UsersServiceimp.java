package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Models.Users;
import com.BackendIkcard.IkcardBackend.Repository.UsersRepository;
import com.BackendIkcard.IkcardBackend.Service.UsersService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceimp implements UsersService {
    private UsersRepository userRepository;



    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
