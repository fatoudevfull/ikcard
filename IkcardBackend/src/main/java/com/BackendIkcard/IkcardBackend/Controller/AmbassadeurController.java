package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Message.Reponse.MessageResponse;
import com.BackendIkcard.IkcardBackend.Message.Requette.SignupRequest;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import com.BackendIkcard.IkcardBackend.Models.ERole;
import com.BackendIkcard.IkcardBackend.Models.Role;
import com.BackendIkcard.IkcardBackend.Repository.AmbassadeurRepository;
import com.BackendIkcard.IkcardBackend.Repository.RoleRepository;
import com.BackendIkcard.IkcardBackend.Service.AmbassadeurService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "user", description = "Les actions réalisables par les utilisateurs du système.")
@RestController
@Controller
@RequestMapping("/ambassadeur")
public class AmbassadeurController {

    private AmbassadeurService ambassadeurService;
    private AmbassadeurRepository ambassadeurRepositoty;
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerAmbassadeur(@Valid @RequestBody SignupRequest signupRequest) {
        if (ambassadeurRepositoty.existsByEmail(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Ce nom d'utilisateur existe déjà!"));
        }

        if (ambassadeurRepositoty.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Cet email est déjà utilisé!"));
        }

        // Create new ambassadeur's account
        Ambassadeur ambassadeur = new Ambassadeur();

        ambassadeur.setUsername(signupRequest.getUsername());
        ambassadeur.setEmail(signupRequest.getEmail());
        ambassadeur.setNom(signupRequest.getNom());
        ambassadeur.setNumero(signupRequest.getNumero());
        ambassadeur.setPassword(encoder.encode(signupRequest.getPassword()));
        Role ambassadeurRole = roleRepository.findByName(ERole.AMBASSADEUR);
        ambassadeur.setRole(ambassadeurRole);

        System.err.println(ambassadeur.getNom());
        if (ambassadeur.getNom() != null) {
            System.err.println("Creer hello");
            System.err.println(ambassadeur.getNom());
            System.err.println(ambassadeur.getRole());
        }

        ambassadeurRepositoty.save(ambassadeur);

        return ResponseEntity.ok(new MessageResponse("Ambassadeur enregistré avec succès!"));
    }

    @PostMapping("/save")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (ambassadeurRepositoty.existsByEmail(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Ce nom d'utilisateur existe déjà!"));
        }

        if (ambassadeurRepositoty.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Cet email est déjà utilisé!"));
        }

        // Create new patient's account
        Ambassadeur user = new Ambassadeur();

        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        encoder.encode(signupRequest.getPassword());
        user.setNom(signupRequest.getNom());
        user.setNumero(signupRequest.getNumero());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        Role userRole = roleRepository.findByName(ERole.ADMINIVEAU2);
        user.setRole(userRole);

        ambassadeurRepositoty.save(user);

        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès!"));
    }

    @GetMapping("/afficher")
    public List<Ambassadeur> Afficher() {
        return ambassadeurService.afficherToutLesAmbassadeur();
    }
}
