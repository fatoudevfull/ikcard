package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Models.Annonce;
import com.BackendIkcard.IkcardBackend.Service.AnnonceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

        @DeleteMapping("/{id}")
        public void deleteAnnonce(@PathVariable Long id) {
            annonceService.deleteAnnonce(id);
        }


}
