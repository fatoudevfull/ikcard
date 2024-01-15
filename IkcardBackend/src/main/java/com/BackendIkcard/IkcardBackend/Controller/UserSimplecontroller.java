package com.BackendIkcard.IkcardBackend.Controller;



import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Jwt.JwtUtils;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.RefreshTokenService;
import com.BackendIkcard.IkcardBackend.Message.Reponse.MessageResponse;
import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Message.Requette.SignupRequest;
import com.BackendIkcard.IkcardBackend.Models.*;
import com.BackendIkcard.IkcardBackend.Repository.RoleRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserSimpleRepository;
import com.BackendIkcard.IkcardBackend.Service.UserSimpleService;
import com.BackendIkcard.IkcardBackend.Service.UsersService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "user", description = "Les actions reslisables par les admin du systeme.")
@RestController
@RequestMapping("/usersimple")
public class UserSimplecontroller {
    @Autowired
    private UserSimpleRepository userSimpleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserSimpleService userSimpleService;
    @Autowired
    private UsersService usersService;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/save")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignupRequest signupRequest) {
        // Vérifier si le nom d'utilisateur existe déjà
        if (userSimpleRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Ce nom d'utilisateur existe déjà!"));
        }
        // Vérifier si l'e-mail existe déjà
        if (userSimpleRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Cet e-mail est déjà utilisé!"));
        }
        // Vérifier si le numéro existe déjà
        if (userSimpleRepository.existsByNumero(signupRequest.getNumero())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Ce numéro est déjà utilisé!"));
        }
        // Vérifier si les champs requis ne sont pas vides
        // Vérifier si le numéro existe déjà
        if (userSimpleRepository.existsByNumero(signupRequest.getNumero())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Ce numéro est déjà utilisé!"));
        }
        // Vérifier si le nom d'utilisateur existe déjà
        if (Objects.equals(signupRequest.getUsername(), ""))  {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Le nom t'utilisateur ne peut etre null!"));
        }
        if (Objects.equals(signupRequest.getEmail(), ""))  {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: L'email ne peut etre null!"));
        }
        if (Objects.equals(signupRequest.getNumero(), ""))  {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Le numero ne peut etre null!"));
        }

        // Create new user's account
        UserSimple user = new UserSimple();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPrenom(signupRequest.getPrenom());
        user.setPhotoProfil(signupRequest.getPhoto());
        // user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setNom(signupRequest.getNom());
        user.setNumero(signupRequest.getNumero());

        Role userRole = roleRepository.findByName(ERole.USER);
        user.setRole(userRole);
        // Set the current date
        user.setDateCreationCompte(new Date());

        userSimpleRepository.save(user);
// Set etat to true before updating
        user.setEtat(true);
        return ResponseEntity.ok(new MessageResponse("utilisateur enregistré avec succès!"));
    }
    @PutMapping("/desactiver/{userId}")
    public ResponseEntity<String> DesactiverCompte(@PathVariable Long userId) {
        userSimpleService.desactiverCompte(userId);
        return new ResponseEntity<>("Compte désactivé avec succès.", HttpStatus.OK);
    }

    @PutMapping("/activer/{userId}")
    public ResponseEntity<String> ActiverCompte(@PathVariable Long userId) {
        userSimpleService.activerCompte(userId);
        return new ResponseEntity<>("Compte activé avec succès.", HttpStatus.OK);
    }

    @DeleteMapping("/supprimer/{id}")
    public ReponseMessage Supprimer(@PathVariable Long id) {
        return userSimpleService.SupprimerUserSimple(id);
    }

    //modifier
    @PutMapping("/modifier/{id}")
    public ReponseMessage Modifier(@PathVariable Long id, @RequestBody UserSimple userSimple) {
        return userSimpleService.modifierUserSimple(id, userSimple);
    }

    @GetMapping("/afficher")
    public List<UserSimple> Afficher() {
        return userSimpleService.afficherToutLesUserSimple();
    }
    // Fin
    //nombre ambassadeur
    @GetMapping("/ambamssadeurCunt")
    public List<Object> nombreAmbassadeur() {
        return userSimpleService.NombreAmbassadeur();
    }
    //nomdre User simple
    @GetMapping("/userCunt")
    public List<Object> nombreUser() {
        return userSimpleService.NombreUser();
    }

    @GetMapping("get/{username}")
    public ResponseEntity<List<Users>> getUsersByUsername(@PathVariable String username) {
        List<Users> users = userSimpleService.getUsersByUsername(username);
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //Nombre user par pays
    @GetMapping("pays/{userpays}")
    public List<Object> nombreUserparpays(@PathVariable String pays) {
        return userSimpleService.NombreUserparpays(pays);
    }
    @PostMapping("/{userId}/photo")
    public ResponseEntity<String> ajouterPhoto(@PathVariable Long userId,
                                               @RequestParam("photo") MultipartFile photo) {
        usersService.ajouterPhoto(userId, photo);
        return ResponseEntity.ok("Photo ajoutée avec succès pour l'utilisateur avec l'ID : " + userId);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImageCouvertureEntreprise(@PathVariable Long id) {
        byte[] image = usersService.getImagephotoProfil(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
}

