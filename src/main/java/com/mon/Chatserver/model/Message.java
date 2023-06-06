package com.mon.Chatserver.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
     private Integer id;
     private String content;
     
     private LocalDateTime timeStamp;
     @ManyToOne
     private User user;

     @ManyToOne 
     @JoinColumn(name="chat_id")
     private Chat chat;

     

    
}
