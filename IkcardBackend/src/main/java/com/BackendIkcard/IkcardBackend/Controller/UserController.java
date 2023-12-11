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


import javax.validation.Valid;
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
    private PasswordEncoder encoder;

    @PostMapping("/save")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignupRequest signupRequest) {
        if (userSimpleRepository.existsByEmail(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Ce nom d'utilisateur existe déjà!"));
        }

        if (userSimpleRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Cet email est déjà utilisé!"));
        }

        // Create new patient's account
        User userSimple = new User();
        userSimple.setUsername(signupRequest.getUsername());
        userSimple.setEmail(signupRequest.getEmail());
        userSimple.setPrenom(signupRequest.getPrenom());
        userSimple.setPhoto(signupRequest.getPhoto());
        userSimple.setPassword(encoder.encode(signupRequest.getPassword()));
        userSimple.setNom(signupRequest.getNom());
        userSimple.setNumero(signupRequest.getNumero());

        Role UserRole = roleRepository.findByName(ERole.USER);
        userSimple.setRole(UserRole);

        userSimpleRepository.save(userSimple);
// Set etat to true before updating
        userSimple.setEtat(true);
        return ResponseEntity.ok(new MessageResponse("Ulilisateur enregistré avec succès!"));
    }

  /*  @PutMapping("/activer/{id}")
    public ResponseEntity<String> activerActiver(@PathVariable("id") Long id) {
        userSimpleService.activerUserSimple(id);
        return ResponseEntity.ok(" activé avec succès.");
    }*/
/*  @PostMapping("/saveWithImage")
  public ResponseEntity<?> registerUserWithImage(@Valid @RequestBody SignupRequest signupRequest,
                                                 @RequestParam("imageFile") MultipartFile imageFile) {
      User newUser = new User(signupRequest.getNom(), signupRequest.getEmail(), signupRequest.getUsername(),
              signupRequest.getNumero(),signupRequest.getPassword(),signupRequest.getPrenom(),signupRequest.getAdresse());
      User savedUser = userService.saveUserWithImage(newUser, imageFile);
      return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès!"));
  }*/

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
}
