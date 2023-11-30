package com.BackendIkcard.IkcardBackend.Controller;



import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Jwt.JwtUtils;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.RefreshTokenService;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.UserDetailsImpl;
import com.BackendIkcard.IkcardBackend.Message.Exeption.TokenRefreshException;
import com.BackendIkcard.IkcardBackend.Message.Reponse.JwtResponse;
import com.BackendIkcard.IkcardBackend.Message.Reponse.ResponseMessage;
import com.BackendIkcard.IkcardBackend.Message.Reponse.TokenRefreshResponse;
import com.BackendIkcard.IkcardBackend.Message.Requette.LoginRequest;
import com.BackendIkcard.IkcardBackend.Message.Requette.SignupRequest;
import com.BackendIkcard.IkcardBackend.Message.Requette.TokenRefreshRequest;
import com.BackendIkcard.IkcardBackend.Models.ERole;
import com.BackendIkcard.IkcardBackend.Models.RefreshToken;
import com.BackendIkcard.IkcardBackend.Models.Role;
import com.BackendIkcard.IkcardBackend.Models.User;
import com.BackendIkcard.IkcardBackend.Repository.RoleRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserRepository;
import com.BackendIkcard.IkcardBackend.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "user", description = "Les actions reslisables par les users du systeme.")
@RestController
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RefreshTokenService refreshTokenService;


    ////refersh token
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }


    ////
    ////pour la creation dun user
    @ApiOperation(value = "Pour la creation d'un user.")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseMessage.generateResponse("Erreur", HttpStatus.BAD_REQUEST, "Erreur: Cet nom d'utilisateur existe déjà!");
        }

        if (signUpRequest.getEmail() != null && signUpRequest.getEmail() != "" && userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseMessage.generateResponse("Erreur", HttpStatus.BAD_REQUEST, "Erreur: Cet email existe déjà!");
        }

        if (userRepository.existsByNumero(signUpRequest.getNumero())) {
            return ResponseMessage.generateResponse("Erreur", HttpStatus.BAD_REQUEST, "Erreur: Cet numero de telephone existe déjà!");
        }


        // Create new user's account
        User user = new User(null,signUpRequest.getNom(),signUpRequest.getUsername(),
                signUpRequest.getEmail(),signUpRequest.getNumero(),
                encoder.encode(signUpRequest.getPassword()));

        log.info("Utilisateur crée" + user);
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role citoyenRole = roleRepository.findByName(ERole.ROLE_USER);
            if (citoyenRole == null) {
                log.info("role non trouvé" + citoyenRole);
                return ResponseMessage.generateResponse("Erreur", HttpStatus.BAD_REQUEST, "Erreur: Role nom trouver.");
            } else {
                roles.add(citoyenRole);
            }
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "superadmin":
                        Role superadminRole = roleRepository.findByName(ERole.ROLE_SUPERADMIN);
                        if (superadminRole == null) {
                            //return  ResponseMessage.generateResponse("Erreur",HttpStatus.BAD_REQUEST,"Erreur: Role nom trouver.");
                        } else {
                            roles.add(superadminRole);
                        }
                        break;
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN1);
                        if (adminRole == null) {
                            //return  ResponseMessage.generateResponse("Erreur",HttpStatus.BAD_REQUEST,"Erreur: Role nom trouver.");
                        } else {
                            roles.add(adminRole);
                        }
                        break;
                    // case "citoyen":
                    // Role adminRole = roleRepository.findByName(ERole.ROLE_CITOYEN)
                    //     .orElseThrow(() -> new RuntimeException("Erreur: Role nom trouver."));
                    // roles.add(adminRole);
                    // break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER);
                        if (userRole == null) {
                            //return  ResponseMessage.generateResponse("Erreur",HttpStatus.BAD_REQUEST,"Erreur: Role nom trouver.");
                        } else {
                            roles.add(userRole);
                        }
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        log.info("Utilisateur crée " + user.getUsername());

        return ResponseMessage.generateResponse("ok", HttpStatus.OK, userRepository.save(user));
    }
    /////fin creation user

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


    // methode pour la mise à jour d'un user
    //@PreAuthorize ("hasRole('ROLE_ADMIN','ROLE_CITOYEN')")
    @ApiOperation(value = "Mis à jour d'un user.")
    @PutMapping("/update")
    public ResponseEntity<Object> MiseAJour(@RequestBody User user) {

        User userUpdated = userService.updateUser(user);
        return ResponseMessage.generateResponse("tilisateur modifié avec succes", HttpStatus.OK, userUpdated);

    }
    // Fin

    // methode pour la mise à jour du mots de passe d'un user
    //@PreAuthorize ("hasRole('ROLE_ADMIN','ROLE_CITOYEN')")
    @ApiOperation(value = "Mis à jour du mots de passe d'un user.")
    @PutMapping("/updatePassword")
    public ResponseEntity<Object> MiseAJourPassword(@RequestBody User user) {

        String newPassword = encoder.encode(user.getPassword());

        User Updateuser = userRepository.findById(user.getId()).get();

        Updateuser.setPassword(newPassword);
        User userUpdated = userService.updateUser(Updateuser);
        return ResponseMessage.generateResponse("Mots de passe modifié avec succes", HttpStatus.OK, userUpdated);

    }
    // Fin

    // methode pour la recuperation d'un user a travers son id
    //@PreAuthorize ("hasRole('ROLE_ADMIN','ROLE_CITOYEN')")
    @ApiOperation(value = "recuperation d'un user a travers son id.")
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getUser(@PathVariable("id") Long idUtilisateur) {
        try {
            return ResponseMessage.generateResponse("ok", HttpStatus.OK, userService.getUser(idUtilisateur));

        } catch (Exception e) {
            return ResponseMessage.generateResponse("erreur", HttpStatus.OK, "Erreur lors de la recuperation.");
        }
    }

    // Fin
    // methode pour la recuperation d'un user a travers son numero de telephone
    //@PreAuthorize ("hasRole('ROLE_ADMIN','ROLE_CITOYEN')")
    @ApiOperation(value = "recuperation d'un user a travers son numero de telephone.")
    @GetMapping("/getuser/{numero}")
    public ResponseEntity<Object> getUserByNumero(@PathVariable("numero") String numero) {
        try {
            return ResponseMessage.generateResponse("ok", HttpStatus.OK, userService.getByNumero(numero));

        } catch (Exception e) {
            return ResponseMessage.generateResponse("erreur", HttpStatus.OK, "Erreur lors de la recuperation.");
        }
    }
    // Fin

    // methode pour la surpression d'un user
    //@PreAuthorize ("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Surpression d'un user.")
    @DeleteMapping("/delete/{idUser}")
    public ResponseEntity<Object> SuprimerUser(@PathVariable Long idUtilisateur) {

        try {
            User user = userRepository.findById(idUtilisateur).get();
            userService.deleteUser(user);
            return ResponseMessage.generateResponse("ok", HttpStatus.OK, "Utilisarteur suprimer!");

        } catch (Exception e) {
            return ResponseMessage.generateResponse("erreur", HttpStatus.OK, "Erreur lors de la surpression.");
        }

    }
    // Fin

    // methode pour la liste des users
    @ApiOperation(value = "Récuperation de la liste des users.")
    //@PreAuthorize ("hasRole('ROLE_ADMIN')")
    @GetMapping("/liste")
    public ResponseEntity<Object> Liste(Authentication authentication) {

        try {
            return ResponseMessage.generateResponse("ok", HttpStatus.OK, userService.getAllUser());

        } catch (Exception e) {
            return ResponseMessage.generateResponse("erreur", HttpStatus.OK, "Erreur lors du retour de la liste.");
        }

    }
    // Fin

    // methode pour la liste des Admins
    @ApiOperation(value = "Récuperation de la liste des administrateurs.")
    //@PreAuthorize ("hasRole('ROLE_ADMIN')")
    @GetMapping("/listeAdmin")
    public ResponseEntity<Object> ListeAdmin(Authentication authentication) {

        try {
            return ResponseMessage.generateResponse("ok", HttpStatus.OK, userService.getAllAdmin());

        } catch (Exception e) {
            return ResponseMessage.generateResponse("erreur", HttpStatus.OK, "Erreur lors du retour de la liste.");
        }

    }
    // Fin

    // Nombre d'admins
    @ApiOperation(value = "Récuperation du nombre d'admins.")
    //@PreAuthorize ("hasRole('ROLE_ADMIN')")
    @GetMapping("/nbre/admin")
    public ResponseEntity<Object> NombreAdmin(Authentication authentication) {

        try {
            return ResponseMessage.generateResponse("ok", HttpStatus.OK, userService.NombreAdmin());

        } catch (Exception e) {
            return ResponseMessage.generateResponse("erreur", HttpStatus.OK, "Erreur lors du retour du nombre d'admins.");
        }

    }
    // Fin

/*    // Nombre de citoyen
    @ApiOperation(value = "Récuperation du nombre de citoyen.")
    //@PreAuthorize ("hasRole('ROLE_ADMIN')")
    @GetMapping("/nbre/citoyen")
    public ResponseEntity<Object> NombreCitoyen(Authentication authentication) {

        try {
            return ResponseMessage.generateResponse("ok", HttpStatus.OK, userService.NombreCitoyen());

        } catch (Exception e) {
            return ResponseMessage.generateResponse("erreur", HttpStatus.OK, "Erreur lors du retour du nombre de citoyen.");
        }

    }
    // Fin


    // methode pour la liste des Citoyens
    @ApiOperation(value = "Récuperation de la liste des citoyens.")
    //@PreAuthorize ("hasRole('ROLE_SUPERADMIN')")
    @GetMapping("/listeCitoyen")
    public ResponseEntity<Object> ListeCitoyen() {

        try {
            return ResponseMessage.generateResponse("ok", HttpStatus.OK, userService.getAllCitoyen());

        } catch (Exception e) {
            return ResponseMessage.generateResponse("erreur", HttpStatus.OK, "Erreur lors du retour de la liste.");
        }

    }
    // Fin*/


}
