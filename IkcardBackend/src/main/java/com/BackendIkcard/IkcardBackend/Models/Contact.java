package com.BackendIkcard.IkcardBackend.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@Table
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String nomComplet;

    public String email;

    public String email1;

    public String photoProfil;

    public String mobile1;

    public String mobile2;

    public String mobile3;

    public String fixe1;

    public String fixe2;
    public boolean etat;

    public String compagnie;

    public String posteOccupe;

    public String profession;

    public String addresse;

    public String facebookLink;

    public String whatsappLink;

    public String instagramLink;

    public String tweetterLink;

    public String linkedinLink;

    public String tiktoklink;

    public String webSite;

    public String autrelink;
    @ManyToOne
    private User user;
}
