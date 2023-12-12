package com.BackendIkcard.IkcardBackend.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "qrcode_data_id", referencedColumnName = "id")
    private QRCodeData qrCodeData;

    // Other fields and annotations

   /* @OneToMany
    private List<Carte> cartes = new ArrayList<>();*/

    @ManyToOne
    private Role role;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "idUtilisateur"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<>();
}
