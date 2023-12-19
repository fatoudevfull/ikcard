package com.BackendIkcard.IkcardBackend.Message.Requette;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;
@Getter
@Setter
public class SignupRequest {
    private String nom;
    @NotBlank
    @Size(max = 20)
    private String username;
    @NotBlank
    private String numero;
    @NotBlank
    private String prenom;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    private String photo;
    @NotBlank
    private String Pays;
    @NotBlank
    private String ville;
    @NotBlank
    private String adresse;
    @NotBlank
    private String imageCouverture;
    @Getter
    private boolean ambassadeur;

    // ... Constructeur, getters et setters ...

    public void setAmbassadeur(boolean ambassadeur) {
        this.ambassadeur = ambassadeur;
    }

    @Getter
    private String lienReferencement;

    public void setLienReferencement(String lienReferencement) {
        this.lienReferencement = lienReferencement;
    }


    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    @NotBlank
    private String roleName;


    public String getRoleName() {
        return roleName;
    }


    private Set<String> role;


}
