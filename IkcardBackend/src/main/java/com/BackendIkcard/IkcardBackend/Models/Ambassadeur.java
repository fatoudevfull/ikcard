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
public class Ambassadeur extends Users {

    private String lienReferencement;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "qrcode_data_id", referencedColumnName = "id")
    private QRCodeData qrCodeData;


    @ManyToOne
    private Role role;
}
