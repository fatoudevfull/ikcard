package com.BackendIkcard.IkcardBackend.Models;


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
   public long IdAnnonce;

  public String TitreAnnonce;

public String Image;

public String Contenu;
@ManyToOne
    private Administrateur administrateur;
}
