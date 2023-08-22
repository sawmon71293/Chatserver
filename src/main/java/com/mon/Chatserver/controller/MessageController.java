package com.mon.Chatserver.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mon.Chatserver.Exception.ChatException;
import com.mon.Chatserver.Exception.UserException;
import com.mon.Chatserver.model.Message;
import com.mon.Chatserver.model.User;
import com.mon.Chatserver.request.SendMessageRequest;
import com.mon.Chatserver.response.ApiResponse;
import com.mon.Chatserver.service.MessageService;
import com.mon.Chatserver.service.UserService;

@RestController
@RequestMapping("api/messages")
@CrossOrigin
public class MessageController {
    private MessageService messageService;
    private UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
       this.messageService=messageService;
       this.userService=userService;
    }


    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest req,@RequestHeader("Authorization")String jwt) throws UserException, ChatException{
        User user =userService.findUserProfile(jwt);
        req.setUserId(user.getId());
        Message message =messageService.sendMessage(req);

        return new ResponseEntity<Message>(message,HttpStatus.OK);

    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatMessagesHandler(@PathVariable Integer chatId,@RequestBody SendMessageRequest req,@RequestHeader("Authorization")String jwt) throws UserException, ChatException{
        User user =userService.findUserProfile(jwt);
       
        List<Message> messages =messageService.getChatsMessages(chatId,user);

        return new ResponseEntity<List<Message>>(messages,HttpStatus.OK);

    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId,@RequestBody SendMessageRequest req,@RequestHeader("Authorization")String jwt) throws UserException, ChatException{
        User user =userService.findUserProfile(jwt);
       messageService.deleteMessage(messageId,user);
        ApiResponse res= new ApiResponse("Delete Message successfully", true);
        return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);

    }



}
