package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Models.Annonce;
import com.BackendIkcard.IkcardBackend.Service.AnnonceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;


import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "admin", description = "Les actions reslisables par les admin du systeme.")
@RestController
@Controller
@RequestMapping("/anonce")
public class AnnonceController {


        @Autowired
        private AnnonceService annonceService;

        @GetMapping
        public List<Annonce> getAllAnnonces() {
            return annonceService.getAllAnnonces();
        }

        @GetMapping("/{id}")
        public Annonce getAnnonceById(@PathVariable Long id) {
            return annonceService.getAnnonceById(id).orElse(null); // Handle not found case
        }

       /* @PostMapping("/add")
        public Annonce createAnnonce(@RequestBody Annonce annonce) {
            System.out.println("id de l'anonce");
            System.out.println(annonce.id);
            System.out.println(annonce.Contenu);
            return annonceService.createAnnonce(annonce);
        }*/
       @PostMapping("/ajout")
       public Annonce createAnnonce(
               @RequestParam("titre") String titre,
               @RequestParam("contenu") String contenu,
               @RequestParam("image") MultipartFile image,
               @RequestParam("administrateurId") Long administrateurId
       ) throws IOException {
           return annonceService.createAnnonceWithImage(titre, contenu, image, administrateurId);
       }

        @PutMapping("/{id}")
        public Annonce updateAnnonce(@PathVariable Long id, @RequestBody Annonce updatedAnnonce) {
            return annonceService.updateAnnonce(id, updatedAnnonce);
        }

  /*  @PutMapping("/activer/{id}")
    public ResponseEntity<String> activerActiver(@PathVariable("id") Long id) {
        annonceService.activerAnonce(id);
        return ResponseEntity.ok(" activé avec succès.");
    }*/
  @PutMapping("/desactiver/{userId}")
  public ResponseEntity<String> desactiverCompte(@PathVariable Long userId) {
      annonceService.desactiverCompte(userId);
      return new ResponseEntity<>("Compte désactivé avec succès.", HttpStatus.OK);
  }

    @PutMapping("/activer/{userId}")
    public ResponseEntity<String> activerCompte(@PathVariable Long userId) {
        annonceService.activerCompte(userId);
        return new ResponseEntity<>("Compte activé avec succès.", HttpStatus.OK);
    }

        @DeleteMapping("/{id}")
        public void deleteAnnonce(@PathVariable Long id) {
            annonceService.deleteAnnonce(id);
        }


}
