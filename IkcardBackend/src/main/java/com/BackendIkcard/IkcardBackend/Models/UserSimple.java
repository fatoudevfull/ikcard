package com.BackendIkcard.IkcardBackend.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class UserSimple extends User {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "qrcode_data_id", referencedColumnName = "id")
    private QRCodeData qrCodeData;



}
