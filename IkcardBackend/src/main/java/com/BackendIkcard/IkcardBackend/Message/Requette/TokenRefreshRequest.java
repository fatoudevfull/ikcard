package com.BackendIkcard.IkcardBackend.Message.Requette;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshRequest {
    
    private String refreshToken;
}
