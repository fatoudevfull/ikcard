package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Jwt.JwtUtils;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.RefreshTokenService;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.UserDetailsImpl;
import com.BackendIkcard.IkcardBackend.Message.Reponse.JwtResponse;
import com.BackendIkcard.IkcardBackend.Message.Reponse.MessageResponse;
import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Message.Requette.LoginRequest;
import com.BackendIkcard.IkcardBackend.Message.Requette.SignupRequest;
import com.BackendIkcard.IkcardBackend.Models.Ambassadeur;
import com.BackendIkcard.IkcardBackend.Models.ERole;
import com.BackendIkcard.IkcardBackend.Models.RefreshToken;
import com.BackendIkcard.IkcardBackend.Models.Role;
import com.BackendIkcard.IkcardBackend.Repository.AdminnistrateurRepository;
import com.BackendIkcard.IkcardBackend.Repository.AmbassadeurRepository;
import com.BackendIkcard.IkcardBackend.Repository.RoleRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserSimpleRepository;
import com.BackendIkcard.IkcardBackend.Service.AmbassadeurService;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "ambassadeur", description = "Les actions reslisables par les admin du systeme.")
@RestController
@RequestMapping("/ambassadeur")
public class AmbassadeurController {

    @Autowired
    private AmbassadeurRepository ambassadeurRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private AmbassadeurService ambassadeurService;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AdminnistrateurRepository adminnistrateurRepository;
    @Autowired
    private UserSimpleRepository userSimpleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/save")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignupRequest signupRequest) {
     /*   if (ambassadeurRepository.existsByEmail(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Ce nom d'utilisateur existe déjà!"));
        }*/
        if (ambassadeurRepository.existsByUsername(signupRequest.getUsername()) ||
                userSimpleRepository.existsByUsername(signupRequest.getUsername()) ||
                ambassadeurRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Ce nom d'utilisateur existe déjà!"));
        }


        if (ambassadeurRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Cet email est déjà utilisé!"));
        }

        // Create new patient's account
        Ambassadeur ambassadeur = new Ambassadeur();
        ambassadeur.setUsername(signupRequest.getUsername());
        ambassadeur.setEmail(signupRequest.getEmail());
        ambassadeur.setPrenom(signupRequest.getPrenom());
        ambassadeur.setPhotoProfil(signupRequest.getPhoto());
        ambassadeur.setPassword(encoder.encode(signupRequest.getPassword()));
        ambassadeur.setNom(signupRequest.getNom());
        ambassadeur.setNumero(signupRequest.getNumero());

        Role ambRole = roleRepository.findByName(ERole.AMBASSADEUR);
        ambassadeur.setRole(ambRole);
        // Set the current date
        ambassadeur.setDateCreationCompte(new Date());

        ambassadeurRepository.save(ambassadeur);
// Set etat to true before updating
        ambassadeur.setEtat(true);
        return ResponseEntity.ok(new MessageResponse("Ambassadeur enregistré avec succès!"));
    }

    @PutMapping("/desactiver/{userId}")
    public ResponseEntity<String> desactiverCompte(@PathVariable Long userId) {
        ambassadeurService.desactiverCompte(userId);
        return new ResponseEntity<>("Compte désactivé avec succès.", HttpStatus.OK);
    }

    @PutMapping("/activer/{userId}")
    public ResponseEntity<String> activerCompte(@PathVariable Long userId) {
        ambassadeurService.activerCompte(userId);
        return new ResponseEntity<>("Compte activé avec succès.", HttpStatus.OK);
    }

    @DeleteMapping("/supprimer/{id}")
    public ReponseMessage Supprimer(@PathVariable Long id) {
        return ambassadeurService.SupprimerAmbassadeur(id);
    }


    //modifier
    @PutMapping("/modifier/{id}")
    public ReponseMessage Modifier(@PathVariable Long id, @RequestBody Ambassadeur ambassadeur) {
        return ambassadeurService.modifierAmbassadeur(id, ambassadeur);
    }

    @GetMapping("/afficher")
    public List<Ambassadeur> Afficher() {
        return ambassadeurService.afficherToutLesAmbassadeur();
    }

    // methode pour le login d'un Admin
/*    @ApiOperation(value = "Le login d'un user.")
    @PostMapping("/login")
    public ResponseEntity<Object> Login(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());


        /////////////////
        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), userDetails.getNumero(), userDetails.getNom(), roles));
    }*/
    // Fin
}
