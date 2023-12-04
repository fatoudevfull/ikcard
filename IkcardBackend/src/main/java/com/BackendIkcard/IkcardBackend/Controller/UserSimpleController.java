package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Message.Reponse.MessageResponse;
import com.BackendIkcard.IkcardBackend.Message.Requette.SignupRequest;
import com.BackendIkcard.IkcardBackend.Models.ERole;
import com.BackendIkcard.IkcardBackend.Models.Role;
import com.BackendIkcard.IkcardBackend.Models.UserSimple;
import com.BackendIkcard.IkcardBackend.Repository.RoleRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserSimpleRepositoty;
import com.BackendIkcard.IkcardBackend.Service.UserSimpleService;
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
@RequestMapping("/userSimple")

public class UserSimpleController {
    @Autowired
    PasswordEncoder encoder;
    private UserSimpleService userSimpleService;
    private UserSimpleRepositoty userSimpleRepositoty;
    private RoleRepository roleRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> registerAmbassadeur(@Valid @RequestBody SignupRequest signupRequest) {
        if (userSimpleRepositoty.existsByEmail(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Ce nom d'utilisateur existe déjà!"));
        }

        if (userSimpleRepositoty.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Cet email est déjà utilisé!"));
        }

        // Create new ambassadeur's account
        UserSimple userSimple = new UserSimple();


        userSimple.setUsername(signupRequest.getUsername());
        userSimple.setEmail(signupRequest.getEmail());
        encoder.encode(signupRequest.getPassword());
        userSimple.setNom(signupRequest.getNom());
        //  ambassadeur.setPrenom(signupRequest.getPrenom());
        //ambassadeur.setPhoto(signupRequest.getPhoto());
        userSimple.setNumero(signupRequest.getNumero());
        userSimple.setPassword(encoder.encode(signupRequest.getPassword()));
        Role userSimpleRole = roleRepository.findByName(ERole.USER);
        // .orElseThrow(() -> new RuntimeException("Erreur: le rôle n'est pas trouvé."));
        userSimple.setRole(userSimpleRole);


        System.err.println(userSimple.getNom());
        if (userSimple.getNom() != null) {
            System.err.println("Creer hello");
            System.err.println(userSimple.getNom());
            System.err.println(userSimple.getRole());
        }

        userSimpleRepositoty.save(userSimple);

        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès!"));
    }

    @GetMapping("/afficher")
    public List<UserSimple> Afficher() {

        return userSimpleService.afficherToutLesUserSimple();
    }

    ///////////////////////////////*************************///////////////
    @PostMapping("/save")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userSimpleRepositoty.existsByEmail(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Ce nom d'utilisateur existe déjà!"));
        }

        if (userSimpleRepositoty.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Cet email est déjà utilisé!"));
        }

        // Create new patient's account
        UserSimple user = new UserSimple();
        System.out.println(user.email);


        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        encoder.encode(signupRequest.getPassword());
        System.out.println(user.email);
        user.setNom(signupRequest.getNom());
        user.setNumero(signupRequest.getNumero());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        System.out.println(user.email);
        Role userRole = roleRepository.findByName(ERole.ADMINIVEAU2);
        user.setRole(userRole);
        System.out.println(user.email);
        System.out.println(user.nom);
        System.out.println(user.getId());


        System.out.println(user);

        userSimpleRepositoty.save(user);
        System.out.println(user);

        return ResponseEntity.ok(new MessageResponse("utilisateur enregistré avec succès!"));
    }
}
