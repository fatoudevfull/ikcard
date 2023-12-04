package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Message.Reponse.MessageResponse;
import com.BackendIkcard.IkcardBackend.Message.Requette.SignupRequest;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import com.BackendIkcard.IkcardBackend.Models.ERole;
import com.BackendIkcard.IkcardBackend.Models.Role;
import com.BackendIkcard.IkcardBackend.Repository.AmbassadeurRepositoty;
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
@Api(value = "user", description = "Les actions reslisables par les users du systeme.")
@RestController
@Controller
@RequestMapping("/ambassadeur")

public class AmbassadeurController {
    private AmbassadeurService ambassadeurService;
    private AmbassadeurRepositoty ambassadeurRepositoty;
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
         Ambassadeur ambassadeur= new Ambassadeur();


        ambassadeur.setUsername(signupRequest.getUsername());
        ambassadeur.setEmail(signupRequest.getEmail());
        encoder.encode(signupRequest.getPassword());
        ambassadeur.setNom(signupRequest.getNom());
      //  ambassadeur.setPrenom(signupRequest.getPrenom());
        //ambassadeur.setPhoto(signupRequest.getPhoto());
        ambassadeur.setNumero(signupRequest.getNumero());
        ambassadeur.setPassword(encoder.encode(signupRequest.getPassword()));
        Role ambassadeurRole = roleRepository.findByName(ERole.AMBASSADEUR);
        // .orElseThrow(() -> new RuntimeException("Erreur: le rôle n'est pas trouvé."));
        ambassadeur.setRole(ambassadeurRole);


        System.err.println(ambassadeur.getNom());
        if (ambassadeur.getNom() != null) {
            System.err.println("Creer hello");
            System.err.println(ambassadeur.getNom());
            System.err.println(ambassadeur.getRole());
            //  mailSender.send(emailConstructor.constructNewUserEmail(patient1,patient1.getCodePatient()));
        }
      //  i = i + 1;

        ambassadeurRepositoty.save(ambassadeur);

        return ResponseEntity.ok(new MessageResponse("Ambassadeur enregistré avec succès!"));
    }
    @GetMapping("/afficher")
    public List<Ambassadeur> Afficher(){

        return ambassadeurService.afficherToutLesAmbassadeur();
    }
}
