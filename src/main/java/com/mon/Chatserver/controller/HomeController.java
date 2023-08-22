package com.mon.Chatserver.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HomeController {


    @GetMapping("/")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("Welcome to our whatsapp api using spring boot");
    }
    
}
