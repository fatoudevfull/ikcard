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
public class UserSimple extends User {

    @ManyToOne
    private Role role;


}
