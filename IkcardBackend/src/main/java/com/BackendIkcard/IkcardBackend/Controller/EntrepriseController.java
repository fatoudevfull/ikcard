package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Message.Reponse.MessageResponse;
import com.BackendIkcard.IkcardBackend.Message.Reponse.ResponseMessage;
import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Message.Requette.SignupRequest;
import com.BackendIkcard.IkcardBackend.Models.ERole;
import com.BackendIkcard.IkcardBackend.Models.Entreprise;
import com.BackendIkcard.IkcardBackend.Models.Role;
import com.BackendIkcard.IkcardBackend.Models.User;
import com.BackendIkcard.IkcardBackend.Repository.EntrepriseRepository;
import com.BackendIkcard.IkcardBackend.Repository.RoleRepository;
import com.BackendIkcard.IkcardBackend.Service.EntrepriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "admin", description = "Les actions reslisables par les admin du systeme.")
@RestController
@Controller
@RequestMapping("/entreprise")
public class EntrepriseController {
  //  private EntrepriseService entrepriseService;
    private EntrepriseRepository entrepriseRepository;

    private final EntrepriseService entrepriseService;

    @Autowired
    public EntrepriseController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

  @PostMapping("/ajouter")
    ReponseMessage Ajouter(@RequestBody Entreprise entreprise) {
      System.out.println(entreprise.email);
      System.out.println(entreprise.getId());
      System.out.println(entreprise.nom);
        return entrepriseService.creerEntreprise(entreprise);
    }

    @PostMapping("/save")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (entrepriseRepository.existsByEmail(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Ce nom d'utilisateur existe déjà!"));
        }

        if (entrepriseRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Cet email est déjà utilisé!"));
        }

        // Create new patient's account
        Entreprise user = new Entreprise();
        System.out.println(user.email);


        user.setEmail(signupRequest.getEmail());
        encoder.encode(signupRequest.getPassword());
        System.out.println(user.email);
        user.setNom(signupRequest.getNom());
        user.setNumero(signupRequest.getNumero());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        System.out.println(user.email);
        Role AminRole = roleRepository.findByName(ERole.ADMINIVEAU2);
        // user.setRoles(AminRole);
        System.out.println(user.email);
        System.out.println(user.nom);
        System.out.println(user.getId());



        System.out.println(user);

        entrepriseRepository.save(user);
        System.out.println(user);

        return ResponseEntity.ok(new MessageResponse("utilisateur enregistré avec succès!"));
    }

//**************************************************Modifier*****************************************
    @GetMapping("/modifier")
    public ReponseMessage Modifier(@RequestBody Entreprise entreprise) {
        return entrepriseService.modifierEntreprise(entreprise);
    }

    @GetMapping("/liste")
    public List<Entreprise> ListeEntreprise() {

      return entrepriseService.afficherToutLesEntreprise();
    }

    @PutMapping("/activer/{id}")
    public ResponseEntity<String> activerActiver(@PathVariable("id") Long id) {
        entrepriseService.activerEntreprise(id);
        return ResponseEntity.ok("Le compte entreprise a été activé activé avec succès.");
    }

    @DeleteMapping("/supprimer")
    public ReponseMessage Supprimer(@PathVariable Long id) {
        return entrepriseService.SupprimerEntreprise(id);
    }

    @ApiOperation(value = "Pour la creation d'un commentaire.")
    @PostMapping("/add")
    public ResponseEntity<?> registerEntreprise(@RequestBody Entreprise entreprise) {

        return ResponseMessage.generateResponse("ok", HttpStatus.OK, entrepriseService.saveEntreprise(entreprise));
    }
}
