package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Models.ERole;
import com.BackendIkcard.IkcardBackend.Models.Role;
import com.BackendIkcard.IkcardBackend.Models.User;
import com.BackendIkcard.IkcardBackend.Repository.RoleRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserRepository;
import com.BackendIkcard.IkcardBackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User a) {
        User User = userRepository.findById(a.getId()).get();
        User log = userRepository.findByUsernameAndPassword(a.getUsername(), a.getPassword());

        if (log == null) {
            a.setPassword(passwordEncoder().encode(a.getPassword()));
        } else {
            a.setPassword(User.getPassword());
        }
        return userRepository.save(a);
    }

    @Override
    public User getUser(Long idUtilisateur) {
        return userRepository.findById(idUtilisateur).get();
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getByUserName(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> null);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> null);
    }

    @Override
    public List<User> getAllAdmin() {
        Role admin = roleRepository.findByName(ERole.ADMINIVEAU1);
        Role superadmin = roleRepository.findByName(ERole.SUPERADMIN);

        List<User> list = new ArrayList<>();
        list.addAll(admin.getUsers());
        list.addAll(superadmin.getUsers());

        return list;
    }

    @Override
    public Long NombreAdmin() {
        Role admin = roleRepository.findByName(ERole.ADMINIVEAU1);
        Role superadmin = roleRepository.findByName(ERole.SUPERADMIN);

        return (long) (admin.getUsers().size() + superadmin.getUsers().size());
    }

    @Override
    public User getByNumero(String numero) {
        return userRepository.findByNumero(numero);
    }
}
