package com.BackendIkcard.IkcardBackend.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@Entity
@Data
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String titre;

    public String Image;

    public String Contenu;
    public boolean etat=true;
    @JsonIgnore
    @ManyToOne
    private Administrateur administrateur;
}
