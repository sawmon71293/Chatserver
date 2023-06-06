package com.mon.Chatserver.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Chat {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private String chat_name;
    private String chat_image;
     
    @ManyToMany
    private List<User> admins;

    @Column(name="is_group")
    private boolean isGroup;
    
    @ManyToOne
    private User createdBy;

    @ManyToMany()
    private Set<User> users= new HashSet<>();
    @OneToMany  
    private List<Message> messages=new ArrayList<>();

    
}
