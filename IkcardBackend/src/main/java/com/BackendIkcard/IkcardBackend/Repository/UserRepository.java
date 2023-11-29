package com.BackendIkcard.IkcardBackend.Repository;


import com.BackendIkcard.IkcardBackend.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByIdUtilisateur(Long idUtilisateur);

   // boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String userName);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);


    Boolean existsByTelephone(String telephone);

    //by telephone
    User findByTelephone(String telephone);
    ///par role
    //List<User> findByRoles(Set<Role> roles);

    User findByUsernameAndPassword(String username, String password);

}
