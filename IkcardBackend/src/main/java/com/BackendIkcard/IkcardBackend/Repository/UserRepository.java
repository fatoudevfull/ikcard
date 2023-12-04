package com.BackendIkcard.IkcardBackend.Repository;


import com.BackendIkcard.IkcardBackend.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
 //For username
 Optional<User> findByUsername(String username);
 Boolean existsByUsername(String username);

 //for email
 Optional<User> findByEmail(String email);
 Boolean existsByEmail(String email);


 Boolean existsByNumero(String numero);

 //by telephone
 User findByNumero(String numero);
 ///par role
 //List<User> findByRoles(Set<Role> roles);

 User findByUsernameAndPassword(String username, String password);

}
