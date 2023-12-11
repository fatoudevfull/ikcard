package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Message.Reponse.MessageResponse;
import com.BackendIkcard.IkcardBackend.Models.Annonce;
import com.BackendIkcard.IkcardBackend.Service.AnnonceService;
import com.BackendIkcard.IkcardBackend.ServiceImplementation.ImageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "admin", description = "Les actions reslisables par les admin du systeme.")
@RestController
@Controller
@RequestMapping("/anonce")
public class AnnonceController {


    private final AnnonceService annonceService;
    private final ImageService imageService;

    @Autowired
    public AnnonceController(AnnonceService annonceService, ImageService imageService) {
        this.annonceService = annonceService;
        this.imageService = imageService;
    }
/*    @PostMapping("/creer")
    public ResponseEntity<String> creerAnnonce(
            @RequestParam("titre") String titre,
            @RequestParam("contenu") String contenu,
            @RequestParam("imageFile") MultipartFile imageFile
    ) {
        Annonce nouvelleAnnonce = new Annonce();
        nouvelleAnnonce.setTitre(titre);
        nouvelleAnnonce.setContenu(contenu);

        annonceService.save(nouvelleAnnonce, imageFile);

        return ResponseEntity.ok("Annonce créée avec succès !");
    }*/



    @PostMapping("/save")
    public ResponseEntity<?> saveAnnonce(@Valid @RequestBody Annonce annonce) {
        Annonce savedAnnonce = annonceService.saveAnnonce(annonce);
        return ResponseEntity.ok(new MessageResponse("Annonce enregistrée avec succès!"));
    }


    @GetMapping
        public List<Annonce> getAllAnnonces() {
            return annonceService.getAllAnnonces();
        }

        @GetMapping("/{id}")
        public Annonce getAnnonceById(@PathVariable Long id) {
            return annonceService.getAnnonceById(id).orElse(null); // Handle not found case
        }


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
