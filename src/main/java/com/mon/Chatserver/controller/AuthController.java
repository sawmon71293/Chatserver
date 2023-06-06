package com.mon.Chatserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mon.Chatserver.Exception.UserException;
import com.mon.Chatserver.config.TokenProvider;
import com.mon.Chatserver.model.User;
import com.mon.Chatserver.repository.UserRepository;
import com.mon.Chatserver.request.LoginRequest;
import com.mon.Chatserver.response.AuthResponse;
import com.mon.Chatserver.service.CustomUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
     private UserRepository userRepository;
     private PasswordEncoder passwordEncoder;
     private TokenProvider tokenProvider;
    private CustomUserService customUserService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,CustomUserService customUserService){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.customUserService=customUserService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException{
          String email=user.getEmail();
          String full_name=user.getFull_name();
          String password = user.getPassword();

          User isUser=userRepository.findByEmail(email);
          if(isUser!=null){
            throw new UserException("Email is used with another account: " + email);
          }

          User createdUser=new User();
          createdUser.setEmail(email);  
          createdUser.setFull_name(full_name);
          createdUser.setPassword(passwordEncoder.encode(password));

         userRepository.save(createdUser);
         Authentication auth=new UsernamePasswordAuthenticationToken(email, createdUser);
         SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt=tokenProvider.generateToken(auth);
        AuthResponse res=new AuthResponse(jwt, true);
        return new ResponseEntity<AuthResponse>(res, HttpStatus.ACCEPTED);
    }


    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req){

          String email=req.getEmail();
          String password=req.getPassword();
          Authentication authentication = authenticate(email, password);
          SecurityContextHolder.getContext().setAuthentication(authentication);
          String jwt = tokenProvider.generateToken(authentication);
          AuthResponse res=new AuthResponse(jwt, true);
          return new ResponseEntity<AuthResponse>(res,HttpStatus.ACCEPTED);
    }

    public Authentication authenticate(String userName,String password){
      UserDetails userdetails = customUserService.loadUserByUsername(userName);
      if(userdetails == null){
        throw new BadCredentialsException("invalid user email");

      }
      if(!passwordEncoder.matches(password, userdetails.getPassword())){
        throw new BadCredentialsException("Invalid password or username" );
      }
      return new UsernamePasswordAuthenticationToken( userdetails, null,userdetails.getAuthorities());
    }
    
}
