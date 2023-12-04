package com.BackendIkcard.IkcardBackend.Models;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ambassadeur extends User {
    public String lienReferencement;
    @ManyToOne
    private Role role;

}
