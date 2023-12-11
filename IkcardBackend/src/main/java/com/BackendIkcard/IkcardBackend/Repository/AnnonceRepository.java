package com.BackendIkcard.IkcardBackend.Repository;


import com.BackendIkcard.IkcardBackend.Models.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
   // void save(Annonce annonce, MultipartFile imageFile);

}
