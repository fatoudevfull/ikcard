package com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services;

import com.BackendIkcard.IkcardBackend.Models.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetailsImpl implements UserDetails{
    
    private Long id;
    private String nom;
    private String prenom;
    private String username;
    private String numero;
    private String email;
    private String photoProfil;
    private String Pays;
    private String ville;
    private String adresse;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;


    public static UserDetailsImpl build(Users user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
            .collect(Collectors.toList());
    
        return new UserDetailsImpl(
            user.getId(), 
            user.getNom(),
            user.getPrenom(),
            user.getUsername(), 
            user.getNumero(),
            user.getEmail(),
            user.getPhotoProfil(),
            user.getPays(),
            user.getVille(),
            user.getAdresse(),
            user.getPassword(),
            authorities);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
        return true;
        if (o == null || getClass() != o.getClass())
        return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
