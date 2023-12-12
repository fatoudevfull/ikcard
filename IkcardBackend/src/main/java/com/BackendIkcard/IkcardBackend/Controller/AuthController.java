package com.BackendIkcard.IkcardBackend.Controller;


import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Jwt.JwtUtils;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.RefreshTokenService;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.UserDetailsImpl;
import com.BackendIkcard.IkcardBackend.Message.Reponse.JwtResponse;
import com.BackendIkcard.IkcardBackend.Message.Reponse.MessageResponse;
import com.BackendIkcard.IkcardBackend.Message.Requette.LoginRequest;
import com.BackendIkcard.IkcardBackend.Models.RefreshToken;
import com.BackendIkcard.IkcardBackend.Models.User;
import com.BackendIkcard.IkcardBackend.Repository.UserRepository;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "auth", description = "Les actions reslisables par les admin du systeme.")
@RestController
@RequestMapping("/auth")
public class AuthController {

  private static final Logger Log = LoggerFactory.getLogger(AuthController.class);
  RefreshTokenService refreshTokenService;
  private static final Logger log = LoggerFactory.getLogger(AuthController.class);


  // ...

/*  // Generate JWT token
  String jwt = jwtUtils.generateJwtToken(authentication);

  // Get user details from authentication
  UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
  List<String> roles = userDetails.getAuthorities().stream()
          .map(item -> item.getAuthority())
          .collect(Collectors.toList());

  // Create and save a refresh token
  RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

  // Return JWT response
  JwtResponse jwtResponse = new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
          userDetails.getUsername(), userDetails.getEmail(), userDetails.getNumero(),
          userDetails.getNom(), roles);

// Return the ResponseEntity with the JWT response
return ResponseEntity.ok(jwtResponse);*/



  //**************************** DECLATION DES DIFFERENTES INSTANCE ******************************************

  //AUTHENTICATION MANAGER COORDONNE LES DIFFERENTS REQUETTE VERS LES BONS ANDROITS
  @Autowired
  AuthenticationManager authenticationManager;

  //CETTE CLASSE CONTIENT DES INFORMATIONS NECCESSAIRE PERMETTANT LA GENERATION DES TOKEN ET LEURS STOCKAGE
  // DANS LES COOKIES
  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  UserRepository utilisateurRepository;


  //******************* METHODE PERMETTANT D'AUTHENTIFIER UN COLLABORATEUR ***********************************
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
    try {
      log.debug("Tentative d’authentification de l’utilisateur: {}", loginRequest.getUsername());

      // Authenticate the user
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);

      // Generate JWT token
      String jwt = jwtUtils.generateJwtToken(authentication);

      // Get user details from authentication
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      List<String> roles = userDetails.getAuthorities().stream()
              .map(item -> item.getAuthority())
              .collect(Collectors.toList());


      // Create and save a refresh token
      RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

      // Create JWT response
      JwtResponse jwtResponse = new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
              userDetails.getUsername(), userDetails.getEmail(), userDetails.getNumero(),
              userDetails.getNom(), roles);

      // Return the ResponseEntity with the JWT response
      return ResponseEntity.ok(jwtResponse);
    } catch (AuthenticationException e) {
      log.error("Échec de l’authentification de l’utilisateur: {}", loginRequest.getUsername(), e);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom d’utilisateur ou mot de passe non valide");
    }
  }
//******************************



  //************************************** MEHTODE PERMETTANT DE CE DECONNECTER ****************************
  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {

    Log.info("COLLABORATEUR DECONNECTER AVEC SUCCESS");

    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new MessageResponse("DECONNEXION REUSSI"));
  }

  @GetMapping("/getAuserConnected")
  public List<User> getAllUserConnected(){
    return utilisateurRepository.findByEtat(true);
  }

}
