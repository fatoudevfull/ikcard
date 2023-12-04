package com.BackendIkcard.IkcardBackend.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


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
    public boolean etat;
    public String adresse;
    public String numero;
    @ManyToOne
    private Role role;
}
