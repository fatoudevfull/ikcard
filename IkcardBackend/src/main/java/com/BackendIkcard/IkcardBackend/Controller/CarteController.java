package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Message.Exeption.NotFoundException;
import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Carte;
import com.BackendIkcard.IkcardBackend.Models.Contact;
import com.BackendIkcard.IkcardBackend.Models.User;
import com.BackendIkcard.IkcardBackend.Repository.CarteRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserRepository;
import com.BackendIkcard.IkcardBackend.Service.CarteService;
import com.BackendIkcard.IkcardBackend.Service.UserService;
import com.BackendIkcard.IkcardBackend.ServiceImplementation.QRCodeService;
import com.google.zxing.WriterException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Date;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CarteRepository carteRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private QRCodeService qrCodeService;

    // Créer une carte
/*    @PostMapping("/creer/{id}")
    public ResponseEntity<Carte> creerCarte(@RequestBody Carte carte, @PathVariable Long id) {
        // Retrieve the user from the database based on the provided userId
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            // Handle the case where the user with the specified userId is not found
            return ResponseEntity.notFound().build();
        }

        // Set the user for the card
        User user = userOptional.get();
        carte.setUser(user);

        // Assuming carteService.creerCarte method saves the card to the database
        Carte nouvelleCarte = carteService.creerCarte(carte);

        return new ResponseEntity<>(nouvelleCarte, HttpStatus.CREATED);
    }*/
    @PostMapping("/creer/{username}")
    public Carte creerCarte(@RequestBody Carte carte, @PathVariable String username) {
        // Vérifier que le nom d'utilisateur n'est pas null
        if (username == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le nom d'utilisateur ne peut pas être null");
        }

        // Recherche de l'utilisateur par nom d'utilisateur
        Optional<User> userOptional = userRepository.findByUsername(username);

        // Vérifier si l'utilisateur existe
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé avec le nom d'utilisateur : " + username);
        }

        // Associer la carte à l'utilisateur
        User user = userOptional.get();
        carte.setUser(user);

        // Votre logique de création de carte ici
        // Set the current date
        carte.setDateCreationCarte(new Date());

        // Enregistrez la carte dans le référentiel de la carte
        return carteRepository.save(carte);
    }

/*    @PostMapping(value = "/generate/{userId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@PathVariable Long userId, String username) {
        try {
            // Récupérer les informations sur l'utilisateur
            User user = userService.findById(userId);
            System.out.println(user.getEmail());

            // Générer le code QR basé sur les informations de l'utilisateur
            byte[] qrCode = qrCodeService.generateAndSaveQRCode(user);

            // Enregistrer la carte dans la base de données
            Carte carte = new Carte();
            carte.setUser(user);
            // Ajoutez d'autres informations de la carte si nécessaire
            System.out.println(carte.getNomComplet());

            // Vérifier que le nom d'utilisateur n'est pas null
            if (username == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le nom d'utilisateur ne peut pas être null");
            }

            // Recherche de l'utilisateur par nom d'utilisateur
            Optional<User> userOptional = userRepository.findByUsername(username);

            // Vérifier si l'utilisateur existe
            if (userOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé avec le nom d'utilisateur : " + username);
            }

            // Associer la carte à l'utilisateur
            User user = userOptional.get();
            carte.setUser(user);

            // Votre logique de création de carte ici
            // Set the current date
            carte.setDateCreationCarte(new Date());

            // Enregistrez la carte dans le référentiel de la carte
             carteRepository.save(carte);

            return ResponseEntity.ok().body(qrCode);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        }
    */


        @PostMapping("/creernom/{username}")
    public Carte creerCarteAvecNomPrenom(@RequestBody Carte carte, @PathVariable String username) {
        // Vérifier que le nom d'utilisateur n'est pas null
        if (username == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le nom d'utilisateur ne peut pas être null");
        }

        // Recherche de l'utilisateur par nom d'utilisateur
        Optional<User> userOptional = userRepository.findByUsername(username);

        // Vérifier si l'utilisateur existe
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé avec le nom d'utilisateur : " + username);
        }

        // Associer la carte à l'utilisateur
        User user = userOptional.get();
        carte.setUser(user);

        // Set the current date
        carte.setDateCreationCarte(new Date());

        // Définir le nomComplet de la carte comme étant égal au nom et prénom de l'utilisateur
        carte.setNomComplet(user.getPrenom() +" "+ user.getNom()  );

        // Enregistrez la carte dans le référentiel de la carte
        System.out.println(carte.getNomComplet());
        System.out.println(carte.getMobile1());
        return carteRepository.save(carte);
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
}

