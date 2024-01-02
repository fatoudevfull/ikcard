package com.BackendIkcard.IkcardBackend.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Utilisation de l'héritage JOINED
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    private String nom;
    private String email;
    private String numero;
    private String username;
    private String prenom;
    private boolean etat = true;
    private String password;
    private String pays;
    private String ville;
    private String adresse;
    private String photo;
    public Date dateCreationCompte;
    @ManyToOne
    private Role role;



    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "idUtilisateur"),
            inverseJoinColumns = @JoinColumn(name = "role_name"))
    private Set<Role> roles = new HashSet<>();



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Carte> carte = new ArrayList<>();


}
