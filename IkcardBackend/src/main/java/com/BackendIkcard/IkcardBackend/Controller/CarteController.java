package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Carte;
import com.BackendIkcard.IkcardBackend.Models.Contact;
import com.BackendIkcard.IkcardBackend.Service.CarteService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "cartes", description = "Les actions reslisables par les admin du systeme.")
@RestController
@RequestMapping("/cartes")
public class CarteController {


    @Autowired
    private CarteService carteService;

    // Créer une carte
    @PostMapping("/creer")
    public ResponseEntity<Carte> creerCarte(@RequestBody Carte carte) {
        Carte nouvelleCarte = carteService.creerCarte(carte);
        return new ResponseEntity<>(nouvelleCarte, HttpStatus.CREATED);
    }

    // Modifier une carte
/*    @PutMapping("/modifier/{id}")
    public ResponseEntity<?> modifierCarte(@RequestBody Carte carte, @PathVariable Long id) {
        try {
            Carte carteModifiee = carteService.modifierCarte(carte, id);
            return new ResponseEntity<>(carteModifiee, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Carte introuvable", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("L'ID de la carte ne correspond pas à l'ID fourni", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Une erreur est survenue lors de la modification de la carte", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
    @PutMapping("/modifier/{carteId}")
    public ReponseMessage modifierCarte(@PathVariable Long carteId, @RequestBody Carte cartetModifie) {
        return carteService.modifierCarte(carteId, cartetModifie);
    }





    //Afficher carte


        @GetMapping("/afficher")
        public ResponseEntity<List<Carte>> afficherTousCartes() {
            List<Carte> cartes = carteService.getAllCartes();
            return new ResponseEntity<>(cartes, HttpStatus.OK);
        }


    // Afficher une carte par ID
    @GetMapping("/afficher/{id}")
    public ResponseEntity<Carte> afficherCarteParId(@PathVariable Long id) {
        Optional<Carte> carte = carteService.afficherCarteParId(id);
        return carte.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Supprimer une carte par ID
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerCarte(@PathVariable Long id) {
        carteService.supprimerCarte(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

