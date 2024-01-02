package com.BackendIkcard.IkcardBackend.Models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Administrateur extends Users {
    @ManyToOne
    private Role role;


}
