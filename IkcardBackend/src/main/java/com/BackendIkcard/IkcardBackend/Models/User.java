package com.BackendIkcard.IkcardBackend.Models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
// @ToString
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String nom;
   @Column(unique = true)
    public String email;
    public String numero;
    @Column(unique = true)
    public String username;
    @Lob
    private String password;
    public String prenom;
    public boolean etat;
    private String Pays;
    private String ville;
    private String adresse;



    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "idUtilisateur"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<Role> roles = new HashSet<>();

    public User(Object o, String nom, String username, String email, String numero, String encode) {
    }
}
