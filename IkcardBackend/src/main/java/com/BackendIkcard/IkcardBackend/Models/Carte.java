package com.BackendIkcard.IkcardBackend.Models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@Table
@NoArgsConstructor
public class Carte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String nomComplet;
    public String email1;
    public String Email2;
    public String photoProfil;
    public String photoCouverture;
    public String Mobile1;
    public String Mobile2;
    public String Mobile3;
    public String Fixe1;
    public String Fixe2;
    public String Compagnie;
    public String posteOccupe;
    public String Profession;
    public String Catalogue;
    public String Addresse;
    public String facebookLink;
    public String whatsappLink;
    public String instagramLink;
    public String tweeterLink;
    public String linkedinLink;
    public String tiktoklink;
    public String webSite;
    public String autrelink;
    public Date dateCreationCarte;
    public boolean etat;


    @ManyToOne
    private User user;



    @Getter
    @Lob
    private byte[] qrCode;
    // Constructors, getters, setters, etc.

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }

}
