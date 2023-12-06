package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Message.Reponse.MessageResponse;
import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "admin", description = "Les actions reslisables par les admin du systeme.")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminnistrateurRepository adminnistrateurRepository;

    @Autowired
    private AdministrateurService administrateurService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

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
        administrateur.setUsername(signupRequest.getUsername());
        administrateur.setEmail(signupRequest.getEmail());
        administrateur.setPassword(encoder.encode(signupRequest.getPassword()));
        administrateur.setNom(signupRequest.getNom());
        administrateur.setNumero(signupRequest.getNumero());

        Role adminRole = roleRepository.findByName(ERole.ADMINIVEAU2);
        administrateur.setRole(adminRole);

        adminnistrateurRepository.save(administrateur);
// Set etat to true before updating
        administrateur.setEtat(true);
        return ResponseEntity.ok(new MessageResponse("Adminnistrateur enregistré avec succès!"));
    }

    @PutMapping("/activer/{id}")
    public ResponseEntity<String> activerActiver(@PathVariable("id") Long id) {
        administrateurService.activerAdmin(id);
        return ResponseEntity.ok(" activé avec succès.");
    }

    @DeleteMapping("/supprimer/{id}")
    public ReponseMessage Supprimer(@PathVariable Long id) {
        return administrateurService.SupprimerAdministrateur(id);
    }


    //modifier
    @PutMapping("/modifier/{id}")
    public ReponseMessage Modifier(@PathVariable Long id, @RequestBody Administrateur administrateur) {
        return administrateurService.modifierAdministrateur(id, administrateur);
    }

    @GetMapping("/afficher")
    public List<Administrateur> Afficher() {
        return administrateurService.afficherToutLesAdministrateur();
    }
}
