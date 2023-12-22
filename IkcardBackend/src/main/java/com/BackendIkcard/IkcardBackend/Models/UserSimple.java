package com.BackendIkcard.IkcardBackend.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserSimple extends Users {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "qrcode_data_id", referencedColumnName = "id")
    private QRCodeData qrCodeData;



}
