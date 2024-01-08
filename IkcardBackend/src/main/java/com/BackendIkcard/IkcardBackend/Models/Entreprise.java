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
public class Entreprise extends Users {

    private String imageCouverture;  // Champ pour stocker le nom du fichier de l'image de couverture
    private String imageCouvertureType;  // Champ pour stocker le type de l'image de couverture

    @Lob
    private byte[] imageCouvertureData;  // Champ pour stocker les données de l'image de couverture

    @ManyToOne
    private Role role;

}
