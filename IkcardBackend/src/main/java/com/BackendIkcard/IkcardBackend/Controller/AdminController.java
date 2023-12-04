package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Message.Reponse.MessageResponse;
import com.BackendIkcard.IkcardBackend.Message.Requette.SignupRequest;
import com.BackendIkcard.IkcardBackend.Models.Administrateur;
import com.BackendIkcard.IkcardBackend.Models.ERole;
import com.BackendIkcard.IkcardBackend.Models.Role;
import com.BackendIkcard.IkcardBackend.Repository.AdminnistrateurRepository;
import com.BackendIkcard.IkcardBackend.Repository.RoleRepository;
import com.BackendIkcard.IkcardBackend.Service.AdministrateurService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "admin", description = "Les actions reslisables par les admin du systeme.")
@RestController
@Controller
@RequestMapping("/admin")
public class AdminController {
    private AdminnistrateurRepository adminnistrateurRepository;
    private AdministrateurService administrateurService;
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/save")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignupRequest signupRequest) {
        if (adminnistrateurRepository.existsByEmail(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Ce nom d'utilisateur existe déjà!"));
        }

        if (adminnistrateurRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Cet email est déjà utilisé!"));
        }

        // Create new patient's account
        Administrateur administrateur = new Administrateur();
        System.out.println(administrateur.email);


        administrateur.setUsername(signupRequest.getUsername());
        administrateur.setEmail(signupRequest.getEmail());
        encoder.encode(signupRequest.getPassword());
        System.out.println(administrateur.email);
        administrateur.setNom(signupRequest.getNom());
        administrateur.setNumero(signupRequest.getNumero());
        administrateur.setPassword(encoder.encode(signupRequest.getPassword()));
        System.out.println(administrateur.email);
        Role AminRole = roleRepository.findByName(ERole.ADMINIVEAU2);
        administrateur.setRole(AminRole);
        System.out.println(administrateur.email);



        System.out.println(administrateur);

        adminnistrateurRepository.save(administrateur);
        System.out.println(administrateur);

        return ResponseEntity.ok(new MessageResponse("Adminnistrateur enregistré avec succès!"));
    }



}
