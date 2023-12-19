package com.BackendIkcard.IkcardBackend.Controller;


import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Jwt.JwtUtils;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.RefreshTokenService;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.UserDetailsImpl;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.UserDetailsServiceImpl;
import com.BackendIkcard.IkcardBackend.Message.Reponse.JwtResponse;
import com.BackendIkcard.IkcardBackend.Message.Reponse.MessageResponse;
import com.BackendIkcard.IkcardBackend.Message.Reponse.UserInfoResponse;
import com.BackendIkcard.IkcardBackend.Message.Requette.LoginRequest;
import com.BackendIkcard.IkcardBackend.Models.Users;
import com.BackendIkcard.IkcardBackend.Repository.UsersRepository;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  UsersRepository utilisateurRepository;

  // This assumes JwtResponse is a simple POJO
  private JwtResponse jwtResponse = new JwtResponse();

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  //**************************** DECLATION DES DIFFERENTES INSTANCE ******************************************

  //AUTHENTICATION MANAGER COORDONNE LES DIFFERENTS REQUETTE VERS LES BONS ANDROITS
  @Autowired
  AuthenticationManager authenticationManager;

  //CETTE CLASSE CONTIENT DES INFORMATIONS NECCESSAIRE PERMETTANT LA GENERATION DES TOKEN ET LEURS STOCKAGE
  // DANS LES COOKIES



  //******************* METHODE PERMETTANT D'AUTHENTIFIER UN COLLABORATEUR ***********************************
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

    //LA METHODE AUTHENTICATE PERMET D'AUTHENFIER UN UTILISATEUR EN FONCTION DU TYPE D'AUTHENTIFICATION
    //DANS NOTRE CAS ON S'AUTHENTIFIE PAR MOT DE PASSE ET LE NOM D'UTILISATEUR
    Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    //C'EST GRACE A SECURITYCONTEXTHOLDER QUE SPRING SECURITY STOCKE LES DETAILS DE CELUI QUI CEST AUTHENTIFIE
    SecurityContextHolder.getContext().setAuthentication(authentication);

    //ICI NOUS CREEONS UNE INSTANCE DE CELUI QUI S'EST AUTHENTIFIER EN UTILISANT LA CLASSE USERDETAILSIMPL
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    //ON GENERE LE TOKEN EN LE STOCKANT DIRECTEMENT DANS UN COOKIE
    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
    String jwt = jwtUtils.generateJwtToken(authentication);
    List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    List<String> entite = new ArrayList<>();

    roles.forEach(role ->{
      entite.add(role);
    });

    Log.info("VOUS ETES AUTHENTIFIE AVEC SUCCESS");

    //METHODE PERMETTANT DE RETOURNER LES INFOS DE USER ET DE STOCKER LE JWT DANS LE COOKIES DE POSTMAN
    ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(new UserInfoResponse(userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles));

    //CHANGEMENT DE LA STATUS DE L'UTILISATEUR
    Users utilisateur = utilisateurRepository.getReferenceById(userDetails.getId());
    utilisateur.setEtat(true);
    utilisateurRepository.save(utilisateur);
    //On RETOURNE LE TOKEN ET LES INFOS DE UTILISATEURS
    return ResponseEntity.ok(new JwtResponse(jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),

            roles));

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
  public List<Users> getAllUserConnected(){
    return utilisateurRepository.findByEtat(true);
  }

}
