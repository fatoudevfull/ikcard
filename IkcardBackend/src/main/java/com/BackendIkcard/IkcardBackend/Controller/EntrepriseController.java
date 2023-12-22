package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Jwt.JwtUtils;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.RefreshTokenService;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.UserDetailsImpl;
import com.BackendIkcard.IkcardBackend.Message.Reponse.JwtResponse;
import com.BackendIkcard.IkcardBackend.Message.Reponse.MessageResponse;
import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Message.Requette.LoginRequest;
import com.BackendIkcard.IkcardBackend.Message.Requette.SignupRequest;
import com.BackendIkcard.IkcardBackend.Models.*;
import com.BackendIkcard.IkcardBackend.Repository.EntrepriseRepository;
import com.BackendIkcard.IkcardBackend.Repository.RoleRepository;
import com.BackendIkcard.IkcardBackend.Service.EntrepriseService;
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
@Api(value = "entreprise", description = "Les actions reslisables par les admin du systeme.")
@RestController
@RequestMapping("/entreprise")
public class EntrepriseController {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private EntrepriseService entrepriseService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/save")
    public ResponseEntity<?> registerEntreprise(@Valid @RequestBody SignupRequest signupRequest) {
        // Check if the username already exists
        if (entrepriseRepository.existsByNom(signupRequest.getNom())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Cet entreprise existe déjà Veuillez choisir un autre nom!"));
        }

        // Check if the email already exists
        if (entrepriseRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Cet email est déjà utilisé Veuillez choisir un autre addresse mail!"));
        }

        // Create new enterprise's account
        Entreprise entreprise = new Entreprise();
        entreprise.setImageCouverture(signupRequest.getImageCouverture());
        entreprise.setEmail(signupRequest.getEmail());
        entreprise.setAdresse(signupRequest.getAdresse());
        entreprise.setPhoto(signupRequest.getPhoto());
        entreprise.setPassword(encoder.encode(signupRequest.getPassword()));
        entreprise.setNom(signupRequest.getNom());
        entreprise.setNumero(signupRequest.getNumero());

        Role entrepriseRole = roleRepository.findByName(ERole.ENTREPRISE);
        entreprise.setRole(entrepriseRole);

        // Set the current date
        entreprise.setDateCreationCompte(new Date());

        // Save the enterprise
        entrepriseRepository.save(entreprise);

        // Set etat to true after saving
        entreprise.setEtat(true);

        return ResponseEntity.ok(new MessageResponse("Entreprise"+" "+ entreprise.getNom()+" "+"est enregistrée avec succès!"));
    }

 @PutMapping("/desactiver/{userId}")
 public ResponseEntity<String> desactiverCompte(@PathVariable Long userId) {
     entrepriseService.desactiverCompte(userId);
     return new ResponseEntity<>("Compte désactivé avec succès.", HttpStatus.OK);
 }

    @PutMapping("/activer/{userId}")
    public ResponseEntity<String> activerCompte(@PathVariable Long userId) {
        entrepriseService.activerCompte(userId);
        return new ResponseEntity<>("Compte activé avec succès.", HttpStatus.OK);
    }

    @DeleteMapping("/supprimer/{id}")
    public ReponseMessage Supprimer(@PathVariable Long id) {
        return entrepriseService.SupprimerEntreprise(id);
    }


    //modifier
    @PutMapping("/modifier/{id}")
    public ReponseMessage Modifier(@PathVariable Long id, @RequestBody Entreprise entreprise) {
        return entrepriseService.modifierEntreprise(id, entreprise);
    }

    @GetMapping("/afficher")
    public List<Entreprise> Afficher() {
        return entrepriseService.afficherToutLesEntreprise();
    }

    // methode pour le login d'un Admin
    @ApiOperation(value = "Le login d'un user.")
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
    }
    // Fin
}
