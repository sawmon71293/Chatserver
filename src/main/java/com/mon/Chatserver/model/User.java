package com.mon.Chatserver.model;

import javax.persistence.Entity;
import javax.persistence.Id;

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
public class User {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private String full_name;
    private String email;
    private String profile_picture;
    private String password;
    }
