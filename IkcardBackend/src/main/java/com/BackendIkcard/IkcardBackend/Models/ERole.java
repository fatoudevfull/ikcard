package com.BackendIkcard.IkcardBackend.Models;

import org.springframework.security.core.GrantedAuthority;

public enum ERole implements GrantedAuthority {
    SUPERADMIN,
    ADMINIVEAU1,
    AMBASSADEUR,
    ADMINIVEAU2,
    ENTREPRISE,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}

