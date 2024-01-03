package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Message.Reponse.MessageResponse;
import com.BackendIkcard.IkcardBackend.Models.Annonce;
import com.BackendIkcard.IkcardBackend.Service.AnnonceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

/*    @PostMapping("/save")
    public ResponseEntity<MessageResponse> saveAnnonce(@RequestBody Annonce annonce){
        // Set etat to true after saving
        annonce.setEtat(true);
        annonce.setDateAnnonce(new Date());
        annonceService.createAnnonce(annonce);
        return ResponseEntity.ok(new MessageResponse("annonce enregistré avec succès!"));
    }*/
    @PostMapping("/save")
    public ResponseEntity<Object> uploadAnnonce(@RequestParam("file") MultipartFile file,
                                                @RequestBody Annonce annonce) throws IOException {
        annonce.setFileName(file.getOriginalFilename());
        annonce.setFileType(file.getContentType());
        annonce.setData(file.getBytes());

        Annonce savedAnnonce = annonceService.createAnnonce(annonce);

        // Return response with the saved Annonce entity or any other response as needed
        return ResponseEntity.ok(savedAnnonce);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Annonce> getAnnonceById(@PathVariable Long id) {
        Annonce annonce = annonceService.getAnnonceById(id);
        return ResponseEntity.ok().body(annonce);
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
