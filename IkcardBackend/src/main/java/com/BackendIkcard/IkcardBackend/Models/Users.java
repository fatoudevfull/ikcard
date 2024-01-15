package com.BackendIkcard.IkcardBackend.Models;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Utilisation de l'héritage JOINED
@Data
@AllArgsConstructor
@NoArgsConstructor
public  class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    private String nom;
    private String email;
    private String numero;
    private String username;
    private String prenom;
    private boolean etat = true;
    private String password;
    private String pays;
    private String ville;
    private String adresse;
    private String photoProfil;
    private String photoType;  // Champ pour stocker le type  de photo

    @Lob
    private byte[] photoData;  // Champ pour stocker les données de photo

    public Date dateCreationCompte;
    @ManyToOne
    private Role role;



    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "idUtilisateur"),
            inverseJoinColumns = @JoinColumn(name = "role_name"))
    private Set<Role> roles = new HashSet<>();



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Carte> carte = new ArrayList<>();

    public byte[] generateQRCode() throws IOException, WriterException {
        String userInfo = String.format("Nom: %s\nEmail: %s\nNumero: %s\nUsername: %s\nPrenom: %s",
                nom, email, numero, username, prenom);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(userInfo, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return outputStream.toByteArray();
    }




}
