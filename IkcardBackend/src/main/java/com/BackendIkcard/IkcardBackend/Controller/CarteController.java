package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Carte;
import com.BackendIkcard.IkcardBackend.Models.Users;
import com.BackendIkcard.IkcardBackend.Models.UserSimple;
import com.BackendIkcard.IkcardBackend.Repository.CarteRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserSimpleRepository;
import com.BackendIkcard.IkcardBackend.Service.CarteService;
import com.BackendIkcard.IkcardBackend.Service.UserSimpleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "cartes", description = "Les actions reslisables par les admin du systeme.")
@RestController
@RequestMapping("/cartes")
public class CarteController {


    @Autowired
    private CarteService carteService;
    @Autowired
    private UserSimpleRepository userRepository;
    @Autowired
    private CarteRepository carteRepository;
    @Autowired
    private UserSimpleService userSimpleService;



 /*   @PostMapping("/creernom/{username}")
    public Carte creerCarteAvecNomPrenom(@RequestBody Carte carte, @PathVariable String username) {
        // Vérifier que le nom d'utilisateur n'est pas null
        if (username == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le nom d'utilisateur ne peut pas être null");
        }

        // Recherche de l'utilisateur par nom d'utilisateur
        Optional<UserSimple> userOptional = userRepository.findByUsername(username);

        // Vérifier si l'utilisateur existe
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé avec le nom d'utilisateur : " + username);
        }

        // Associer la carte à l'utilisateur
        Users user = userOptional.get();
        carte.setUser(user);

        // Set the current date
        carte.setDateCreationCarte(new Date());

        // Définir le nomComplet de la carte comme étant égal au nom et prénom de l'utilisateur
        carte.setNomComplet(user.getPrenom() + " " + user.getNom());

        // Enregistrez la carte dans le référentiel de la carte
        System.out.println(carte.getNomComplet());
        System.out.println(carte.getMobile1());
        return carteRepository.save(carte);
    }*/

    @PostMapping("/create/{username}")
    public ResponseEntity<Carte> createCarte(@RequestBody Carte carte, @PathVariable String username) {
        try {
            Carte createdCarte = carteService.creerCarteAvecNomPrenom(carte, username);
            return new ResponseEntity<>(createdCarte, HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // Modifier une carte
    @PutMapping("/modifier/{carteId}")
    public ReponseMessage modifierCarte(@PathVariable Long carteId, @RequestBody Carte cartetModifie) {
        return carteService.modifierCarte(carteId, cartetModifie);
    }
    @PutMapping("/desactiver/{userId}")
    public ResponseEntity<String> desactiverCompte(@PathVariable Long userId) {
        carteService.desactiverCompte(userId);
        return new ResponseEntity<>("Compte désactivé avec succès.", HttpStatus.OK);
    }

    @GetMapping("/{compagnie}")
    public List<Object> compagnieNombre(@PathVariable String compagnie) {
        return carteService.ListeCarteparEntreprise(compagnie);
    }
    @PutMapping("/activer/{userId}")
    public ResponseEntity<String> activerCompte(@PathVariable Long userId) {
        carteService.activerCompte(userId);
        return new ResponseEntity<>("Compte activé avec succès.", HttpStatus.OK);
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

    @PostMapping("/{id}/photo")
    public ResponseEntity<String> ajouterPhoto(@PathVariable Long id,
                                               @RequestParam("photo") MultipartFile photo) {
        carteService.ajouterphotoCouverture(id, photo);
        return ResponseEntity.ok("Photo couverture ajoutée avec succès a la carte : " + id);
    }
}

