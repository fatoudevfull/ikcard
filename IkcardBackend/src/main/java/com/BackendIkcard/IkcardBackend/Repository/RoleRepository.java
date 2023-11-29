package com.BackendIkcard.IkcardBackend.Repository;




import com.BackendIkcard.IkcardBackend.Models.ERole;
import com.BackendIkcard.IkcardBackend.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findByName(ERole name);
 // Role findByIdrole(Long id);


/*  @Modifying
  @Transactional
  @Query(value = "INSERT INTO roles(name) VALUES('ROLE_ADMIN');",nativeQuery = true)
  void createAdmin();
  @Modifying
  @Transactional
  @Query(value = "INSERT INTO roles(name) VALUES('ROLE_Medecin');",nativeQuery = true)
  void creatteMedecin();
  @Modifying
  @Transactional
  @Query(value = "INSERT INTO roles(name) VALUES('ROLE_Patient');",nativeQuery = true)
  void createPatient();*/

}
