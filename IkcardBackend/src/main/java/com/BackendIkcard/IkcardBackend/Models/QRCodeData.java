package com.BackendIkcard.IkcardBackend.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class QRCodeData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] qrCode;

    // Add any other fields or relationships as needed

    // Getters and setters
}
