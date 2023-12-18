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
import com.BackendIkcard.IkcardBackend.Repository.RoleRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserRepository;
import com.BackendIkcard.IkcardBackend.Service.UserService;
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
import org.springframework.beans.factory.annotation.Autowired;


import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "userSimple", description = "Les actions reslisables par les admin du systeme.")
@RestController
@RequestMapping("/userSimple")
public class UserController {

    @Autowired
    private UserRepository userSimpleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserService userSimpleService;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;


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

        // Créer un nouveau compte utilisateur
        User userSimple = new User();
        userSimple.setUsername(signupRequest.getUsername());
        userSimple.setEmail(signupRequest.getEmail());
        userSimple.setPrenom(signupRequest.getPrenom());
        userSimple.setPhoto(signupRequest.getPhoto());
        userSimple.setPassword(encoder.encode(signupRequest.getPassword()));
        userSimple.setNom(signupRequest.getNom());
        userSimple.setNumero(signupRequest.getNumero());

        Role userRole = roleRepository.findByName(ERole.USER);
        userSimple.setRole(userRole);

        // Définir la date actuelle
        userSimple.setDateCreationCompte(new Date());

        // Définir l'état sur true avant la mise à jour
        userSimple.setEtat(true);

        // Enregistrer l'utilisateur
        userSimpleRepository.save(userSimple);

        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès!"));
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
    public ReponseMessage Modifier(@PathVariable Long id, @RequestBody User userSimple) {
        return userSimpleService.modifierUserSimple(id, userSimple);
    }

    @GetMapping("/afficher")
    public List<User> Afficher() {
        return userSimpleService.afficherToutLesUserSimple();
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

    //Nombre user par pays
    @GetMapping("/{userpays}")
    public List<Object> nombreUserparpays(@PathVariable String pays) {
        return userSimpleService.NombreUserparpays(pays);
    }
}
