package com.BackendIkcard.IkcardBackend.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String nom;
    private String password;
    public String photo;
    public String email;
    public boolean etat=true;
    public String adresse;
    public String numero;
    public String imageCouverture;
    public Date dateCreationCompte;

    @ManyToOne
    private Role role;
}
