package com.BackendIkcard.IkcardBackend.Repository;


import com.BackendIkcard.IkcardBackend.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Optional<User> findByUsername(String username);

   // boolean existsByUsername(String username);
   // User findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByNumero(String numero);
    List<User> findByEtat(Boolean status);
    @Query(value = "SELECT COUNT(*) FROM `user` WHERE user.dtype='ambassadeur';",nativeQuery = true)
    List<Object> NombreAmbassadeur();
    @Query(value = "SELECT COUNT(*) FROM `user` WHERE user.dtype='user';",nativeQuery = true)
    List<Object> NombreUser();
    @Query(value = "SELECT COUNT(*) FROM `user` WHERE user.pays=:pays;",nativeQuery = true)
    List<Object> NombreUserParPays(@Param("pays") String pays);



}
