package com.BackendIkcard.IkcardBackend.Controller;


import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Jwt.JwtUtils;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.RefreshTokenService;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.UserDetailsImpl;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.UserDetailsServiceImpl;
import com.BackendIkcard.IkcardBackend.Message.Reponse.JwtResponse;
import com.BackendIkcard.IkcardBackend.Message.Reponse.MessageResponse;
import com.BackendIkcard.IkcardBackend.Message.Requette.LoginRequest;
import com.BackendIkcard.IkcardBackend.Models.RefreshToken;
import com.BackendIkcard.IkcardBackend.Models.Users;
import com.BackendIkcard.IkcardBackend.Repository.UsersRepository;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "auth", description = "Les actions reslisables par les admin du systeme.")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger Log = LoggerFactory.getLogger(AuthController.class);
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UsersRepository utilisateurRepository;
    //AUTHENTICATION MANAGER COORDONNE LES DIFFERENTS REQUETTE VERS LES BONS ANDROITS
    @Autowired
    AuthenticationManager authenticationManager;
    // This assumes JwtResponse is a simple POJO
    private JwtResponse jwtResponse = new JwtResponse();

    //**************************** DECLATION DES DIFFERENTES INSTANCE ******************************************
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    //CETTE CLASSE CONTIENT DES INFORMATIONS NECCESSAIRE PERMETTANT LA GENERATION DES TOKEN ET LEURS STOCKAGE
    // DANS LES COOKIES

    //******************* METHODE PERMETTANT D'AUTHENTIFIER UN COLLABORATEUR ***********************************
    @PostMapping("/signin")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        try {
            log.debug("Attempting to authenticate user: {}", loginRequest.getUsername());

            // Validate input
            if (StringUtils.isEmpty(loginRequest.getPassword())) {
                log.error("Empty password provided for user: {}", loginRequest.getUsername());
                return ResponseEntity.badRequest().body("Le mot de passe ne peut pas être vide");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
         //   RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

         //   RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());


            log.info("User authenticated successfully: {}", loginRequest.getUsername());
            System.out.println(userDetails.getEmail());
            System.out.println(userDetails.getUsername());
            System.out.println(userDetails.getNom());

            return ResponseEntity.ok(new JwtResponse(
                    jwtUtils.generateJwtToken(authentication),
                    refreshToken.getToken(),
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getNom(),
                    userDetails.getPrenom(),
                    roles
            ));

        } catch (BadCredentialsException e) {
            log.error("Bad credentials for user: {}", loginRequest.getUsername(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mauvaises informations d’identification");
        } catch (LockedException e) {
            log.error("User account locked: {}", loginRequest.getUsername(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Le compte d’utilisateur est verrouillé");
        } catch (DisabledException e) {
            log.error("User account disabled: {}", loginRequest.getUsername(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Le compte d’utilisateur est désactivé");
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", loginRequest.getUsername(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Echec de l’authentification");
        }
    }



    //************************************** MEHTODE PERMETTANT DE CE DECONNECTER ****************************
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {

        Log.info("COLLABORATEUR DECONNECTER AVEC SUCCESS");

        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("DECONNEXION REUSSI"));
    }

    @GetMapping("/getAuserConnected")
    public List<Users> getAllUserConnected() {
        return utilisateurRepository.findByEtat(true);
    }

}
