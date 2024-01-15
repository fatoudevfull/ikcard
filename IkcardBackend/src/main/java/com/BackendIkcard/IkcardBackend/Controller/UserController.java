package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Jwt.JwtUtils;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.RefreshTokenService;
import com.BackendIkcard.IkcardBackend.Message.Reponse.MessageResponse;
import com.BackendIkcard.IkcardBackend.Message.Requette.SignupRequest;
import com.BackendIkcard.IkcardBackend.Models.ERole;
import com.BackendIkcard.IkcardBackend.Models.Role;
import com.BackendIkcard.IkcardBackend.Models.UserSimple;
import com.BackendIkcard.IkcardBackend.Repository.AdminnistrateurRepository;
import com.BackendIkcard.IkcardBackend.Repository.AmbassadeurRepository;
import com.BackendIkcard.IkcardBackend.Repository.RoleRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserSimpleRepository;
import com.BackendIkcard.IkcardBackend.Service.UsersService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "user", description = "Les actions reslisables par les admin du systeme.")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserSimpleRepository userSimpleRepository;
    @Autowired
    private AdminnistrateurRepository adminnistrateurRepository;
    @Autowired
    private AmbassadeurRepository ambassadeurRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UsersService userService;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/save")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignupRequest signupRequest) {
        // Vérifier si le nom d'utilisateur existe déjà
     /*   if (userSimpleRepository.existsByUsername(signupRequest.getUsername())) {
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
        // Create new patient's account
        UserSimple user = new UserSimple();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPrenom(signupRequest.getPrenom());
        user.setPhotoProfil(signupRequest.getPhoto());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setNom(signupRequest.getNom());
        user.setNumero(signupRequest.getNumero());
        Role userRole = roleRepository.findByName(ERole.USER);
        user.setRole(userRole);
        // Set the current date
        user.setDateCreationCompte(new Date());
        userSimpleRepository.save(user);
// Set etat to true before updating
        user.setEtat(true);
        return ResponseEntity.ok(new MessageResponse("utilisateur enregistré avec succès!"));
    }



}
