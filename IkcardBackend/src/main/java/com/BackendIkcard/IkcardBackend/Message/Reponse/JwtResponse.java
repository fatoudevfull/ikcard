package com.BackendIkcard.IkcardBackend.Message.Reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    
    private String token;
    private String refreshToken;

    private String type = "Bearer";
    private Long idUtilisateur;
    private String userName;
    private String email;
    private String photo;
    private String nom;
    private String prenom;
    private String numero;

    private String Pays;
    private String ville;
    private String adresse;

    private List<String> roles;

    public JwtResponse(String accessToken,String refreshToken, Long id, String userName,String email,String numero, String nom,String prenom, String photo,String Pays, String ville,String adresse,List<String> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.idUtilisateur = id;
        this.userName = userName;
        this.email = email;
        this.photo = photo;
        this.nom = nom;
        this.prenom = prenom;
        this.roles = roles;
        this.adresse=adresse;
        this.Pays=Pays;
        this.ville=ville;
        this.numero=numero;
      }

    public JwtResponse(String jwt, String token, Long idUtilisateur, String username, String email, String telephone, String nom, List<String> roles) {
    }

    public JwtResponse(String jwt, Long id, String username, String email, String nom, String prenom, List<String> roles) {
    }

    public JwtResponse(String jwt, String username, List<String> roles) {
    }
}
