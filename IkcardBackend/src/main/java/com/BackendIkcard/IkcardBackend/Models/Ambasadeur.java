package com.BackendIkcard.IkcardBackend.Models;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
@Getter
@Setter
@AllArgsConstructor
public class Ambasadeur extends User {
    public String prenom;
    public boolean etat;
    private String Pays;
    private String ville;
    private String adresse;
    public String lienReferencement;
    @ManyToOne
    private Role role;

}
