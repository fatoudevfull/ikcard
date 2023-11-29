package com.BackendIkcard.IkcardBackend.Models;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
public class Administrateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long idAdmim;
    public String nom;
    private String password;
    public String photo;
    public String email;
    public boolean etat;
    @ManyToOne
    private Role role;
}
