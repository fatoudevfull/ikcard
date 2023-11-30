package com.BackendIkcard.IkcardBackend.Models;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Getter
@Setter
@AllArgsConstructor
public class Ambasadeur extends User {
    public String lienReferencement;
    @ManyToOne
    private Role role;

}
