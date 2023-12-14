package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Models.Annonce;
import com.BackendIkcard.IkcardBackend.Service.AnnonceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
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

    @PostMapping("/saveWithFile")
    public ResponseEntity<Annonce> saveAnnonceWithFile(
            @RequestBody Annonce annonce,
            @RequestParam("file") MultipartFile file) {
        Annonce savedAnnonce = annonceService.saveAnnonceWithFile(annonce, file);
        return ResponseEntity.ok().body(savedAnnonce);
    }
    @PostMapping("/save")
    public Annonce saveAnnonce(@RequestBody Annonce annonce){
        // Set the current date
        annonce.setDateAnnonce(new Date());
        return annonceService.createAnnonce(annonce);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Annonce> getAnnonceById(@PathVariable Long id) {
        Annonce annonce = annonceService.getAnnonceById(id);
        return ResponseEntity.ok().body(annonce);
    }

    @PostMapping
    public ResponseEntity<Annonce> createAnnonce(@RequestBody Annonce annonce) {
        Annonce createdAnnonce = annonceService.createAnnonce(annonce);
        return ResponseEntity.ok().body(createdAnnonce);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Annonce> updateAnnonce(@PathVariable Long id, @RequestBody Annonce updatedAnnonce) {
        Annonce updated = annonceService.updateAnnonce(id, updatedAnnonce);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnnonce(@PathVariable Long id) {
        annonceService.deleteAnnonce(id);
        return ResponseEntity.ok().body("Annonce supprimée avec succès");
    }
}
