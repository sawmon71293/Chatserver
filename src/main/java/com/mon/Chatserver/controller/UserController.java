package com.mon.Chatserver.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mon.Chatserver.Exception.UserException;
import com.mon.Chatserver.model.User;
import com.mon.Chatserver.request.UpdateUserRequest;
import com.mon.Chatserver.response.ApiResponse;
import com.mon.Chatserver.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
      this.userService=userService;
    }

    @GetMapping("/profile")
    public  ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization")String token) throws UserException
    {
        User user= userService.findUserProfile(token);
        return  new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
        
    }


    @GetMapping("/{query}")
    public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String query) throws UserException
    {
        List<User> users=userService.searchUser(query);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest req, @RequestHeader("Authorization")String token) throws UserException
    {
        User user =userService.findUserProfile(token);
        userService.updateUser(user.getId(), req);
        ApiResponse response= new ApiResponse("User updated successfully",true);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.ACCEPTED);
    }
}
