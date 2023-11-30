package com.BackendIkcard.IkcardBackend.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Administrateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String nom;
    private String password;
    public String photo;
    public String email;
    @Column(unique = true)
    public String username;
    public boolean etat;
    public String numero;
    @ManyToOne
    private Role role;
}
