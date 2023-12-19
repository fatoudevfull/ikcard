package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Jwt.JwtUtils;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.RefreshTokenService;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.UserDetailsImpl;
import com.BackendIkcard.IkcardBackend.Message.Reponse.JwtResponse;
import com.BackendIkcard.IkcardBackend.Message.Reponse.MessageResponse;
import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Message.Requette.LoginRequest;
import com.BackendIkcard.IkcardBackend.Message.Requette.SignupRequest;
import com.BackendIkcard.IkcardBackend.Models.Administrateur;
import com.BackendIkcard.IkcardBackend.Models.ERole;
import com.BackendIkcard.IkcardBackend.Models.RefreshToken;
import com.BackendIkcard.IkcardBackend.Models.Role;
import com.BackendIkcard.IkcardBackend.Repository.AdminnistrateurRepository;
import com.BackendIkcard.IkcardBackend.Repository.AmbassadeurRepository;
import com.BackendIkcard.IkcardBackend.Repository.RoleRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserSimpleRepository;
import com.BackendIkcard.IkcardBackend.Service.AdministrateurService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "admin", description = "Les actions reslisables par les admin du systeme.")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private AdminnistrateurRepository adminnistrateurRepository;
    @Autowired
    private AmbassadeurRepository ambassadeurRepository;
    @Autowired
    private UserSimpleRepository userSimpleRepository;
    @Autowired
    private AdministrateurService administrateurService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/save")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignupRequest signupRequest) {
        // Check if the username already exists
      /*  if (adminnistrateurRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Ce nom d'utilisateur existe déjà!"));
        }*/
        if (adminnistrateurRepository.existsByUsername(signupRequest.getUsername()) ||
                userSimpleRepository.existsByUsername(signupRequest.getUsername()) ||
                ambassadeurRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Ce nom d'utilisateur existe déjà!"));
        }


        // Check if the email already exists
        if (adminnistrateurRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Cet email est déjà utilisé!"));
        }

        // Vérifier si le numéro existe déjà
        if (adminnistrateurRepository.existsByNumero(signupRequest.getNumero())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Ce numéro est déjà utilisé!"));
        }

        // Vérifier si les champs requis ne sont pas vides
        // Vérifier si le numéro existe déjà
        if (adminnistrateurRepository.existsByNumero(signupRequest.getNumero())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Ce numéro est déjà utilisé!"));
        }
        // Vérifier si le nom d'utilisateur existe déjà
        if (signupRequest.getUsername()=="")  {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Le nom t'utilisateur ne peut etre null!"));
        }
        if (signupRequest.getEmail()=="")  {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: L'email ne peut etre null!"));
        }
        if (signupRequest.getNumero()=="")  {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Le numero ne peut etre null!"));
        }

        // Create new administrator's account
        Administrateur administrateur = new Administrateur();
        administrateur.setUsername(signupRequest.getUsername());
        administrateur.setEmail(signupRequest.getEmail());
        administrateur.setPrenom(signupRequest.getPrenom());
        administrateur.setPhoto(signupRequest.getPhoto());
        administrateur.setPassword(encoder.encode(signupRequest.getPassword()));
        administrateur.setNom(signupRequest.getNom());
        administrateur.setNumero(signupRequest.getNumero());
        System.out.println(administrateur.getPassword());
        System.out.println(administrateur.getUsername());

        // Set role based on the specified roleName
        Role adminRole = roleRepository.findByName(getRoleByName(signupRequest.getRoleName()));
        administrateur.setRole(adminRole);

        // Set etat to true before updating
        administrateur.setEtat(true);


        adminnistrateurRepository.save(administrateur);

        return ResponseEntity.ok(new MessageResponse("Adminnistrateur enregistré avec succès!"));
    }

    // Helper method to get role by name
    private ERole getRoleByName(String roleName) {
        try {
            return ERole.valueOf(roleName);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Role not found: " + roleName);
        }
    }

 @PutMapping("/desactiver/{userId}")
 public ResponseEntity<String> desactiverCompte(@PathVariable Long userId) {
     administrateurService.desactiverCompte(userId);
     return new ResponseEntity<>("Compte désactivé avec succès.", HttpStatus.OK);
 }

    @PutMapping("/activer/{userId}")
    public ResponseEntity<String> activerCompte(@PathVariable Long userId) {
        administrateurService.activerCompte(userId);
        return new ResponseEntity<>("Compte activé avec succès.", HttpStatus.OK);
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

    // methode pour le login d'un Admin
    // Fin
    @ApiOperation(value = "Le login d'un admin.")
    @PostMapping("/login")
    public ResponseEntity<Object> loginAdmin(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl adminDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = adminDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(adminDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), adminDetails.getId(), adminDetails.getUsername(), adminDetails.getEmail(), adminDetails.getNumero(), adminDetails.getNom(), roles));
    }

}
