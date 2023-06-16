package com.mon.Chatserver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mon.Chatserver.Exception.UserException;
import com.mon.Chatserver.config.JwtTokenFilter;
import com.mon.Chatserver.config.JwtTokenUtil;
import com.mon.Chatserver.model.User;
import com.mon.Chatserver.repository.UserRepository;
import com.mon.Chatserver.request.UpdateUserRequest;


@Service
public class UserServiceImplementation implements UserService{

    private UserRepository userRepository;
    private JwtTokenFilter jwtTokenFilter;
    public UserServiceImplementation( UserRepository repository, JwtTokenFilter jwtTokenFilter)
    {
        this.userRepository=repository;
        this.jwtTokenFilter=jwtTokenFilter;
    }
    @Override
    public User findUserById(Integer id) throws UserException {
        Optional<User> opt=userRepository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new UserException("User not found.");

    }

    @Override
    public User findUserProfile(String jwt) throws UserException {
      UserDetails userDetails= jwtTokenFilter.getUserDetails(jwt);
      String email = userDetails.getUsername();
      if(email==null){
        throw new BadCredentialsException("Received Invalid token.");
      }
      User user=userRepository.findByEmail(email);
      if(user==null){
        throw new UserException("User not found.");
      }
      return user;
    }

    @Override
    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
        User user = findUserById(userId);
        if(req.getFull_name()!=null){
            user.setFull_name(req.getFull_name());
        }
        if(req.getProfile_picture() !=null){
            user.setProfile_picture(req.getProfile_picture());
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String query) {
       List<User> users=userRepository.searchUser(query);
       return users;

    }

}