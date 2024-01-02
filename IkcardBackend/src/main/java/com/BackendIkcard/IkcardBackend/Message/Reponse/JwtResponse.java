package com.BackendIkcard.IkcardBackend.Message.Reponse;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private String refreshToken;

    private String photo;
    private String nom;
    private String prenom;
    private String numero;

    private String Pays;
    private String ville;
    private String address;


    public JwtResponse(String accessToken, Long id, String username, String email, String numero, String nom, String prenom,
                       String pays, String ville, String photo, String adresse, String refreshToken, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.numero = numero;
        this.nom = nom;
        this.prenom = prenom;
        this.Pays = pays;
        this.ville = ville;
        this.photo = photo;
        this.address = adresse;
        this.refreshToken = refreshToken;
        this.roles = roles;
    }




    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return (List<String>) roles;
    }
}
