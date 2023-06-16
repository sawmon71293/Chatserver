package com.mon.Chatserver.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String full_name;
    private String email;
    private String profile_picture;
    private String password;
    public UserDetails orElseThrow(Object object) {
        return null;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return null;
    }
    @Override
    public String getUsername() {
        return this.email;

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
    }
