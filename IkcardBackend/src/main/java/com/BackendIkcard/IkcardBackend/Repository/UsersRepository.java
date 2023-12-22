package com.BackendIkcard.IkcardBackend.Repository;

import com.BackendIkcard.IkcardBackend.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);

    Optional<Users> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByNumero(String numero);

    List<Users> findByEtat(Boolean status);
}
