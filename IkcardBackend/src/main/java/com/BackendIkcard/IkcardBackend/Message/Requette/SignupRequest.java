package com.BackendIkcard.IkcardBackend.Message.Requette;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class SignupRequest {
    private String nom;
    private String username;
    private String numero;

    private String email;
   // private String photo;
  //  private String Pays;
 //   private String ville;
   // private String adresse;
    
    //

    private String password;


    private Set<String> role;


}
