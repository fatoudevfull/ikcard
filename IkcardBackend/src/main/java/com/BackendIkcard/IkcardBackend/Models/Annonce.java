package com.BackendIkcard.IkcardBackend.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "annonce")
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Other existing attributes...

    private String titre;
    private Date dateAnnonce;
    private String contenu;
    private boolean etat;

    private String fileName;
    private String fileType;

    @Lob
    private byte[] data;

    // Constructors, getters, setters...
}
