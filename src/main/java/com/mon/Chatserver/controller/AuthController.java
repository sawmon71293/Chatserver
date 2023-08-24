package com.mon.Chatserver.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mon.Chatserver.Exception.UserException;
import com.mon.Chatserver.config.JwtTokenUtil;
import com.mon.Chatserver.model.User;
import com.mon.Chatserver.repository.UserRepository;
import com.mon.Chatserver.request.LoginRequest;
import com.mon.Chatserver.response.AuthResponse;
import com.mon.Chatserver.service.CustomUserService;


@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @Autowired
     private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserService customUserService;

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
          createdUser.setPassword(password);
          createdUser.setPassword(passwordEncoder.encode(password));

          User savedUser=userRepository.save(createdUser);
         Authentication auth=new UsernamePasswordAuthenticationToken(email, password);
         SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt=jwtTokenUtil.generateAccessToken(savedUser);
        AuthResponse res=new AuthResponse(savedUser.getEmail(),jwt);
        return  ResponseEntity.ok(res);
    }

@PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req, HttpServletRequest request){
      User user = new User();
          user.setEmail(req.getEmail());
          user.setPassword(req.getPassword());
          Authentication authentication = authenticate(user.getEmail(), user.getPassword());
          SecurityContextHolder.getContext().setAuthentication(authentication);
          User authUser = userRepository.findByEmail(user.getEmail());
          if (authUser != null) {
             user.setId(authUser.getId());
          }
         
          String jwt = jwtTokenUtil.generateAccessToken(user);
          AuthResponse res=new AuthResponse(user.getEmail(), jwt);
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
      return new UsernamePasswordAuthenticationToken( userdetails, null,null);
    }
    
}
