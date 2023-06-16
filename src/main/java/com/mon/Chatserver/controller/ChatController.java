package com.mon.Chatserver.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mon.Chatserver.Exception.ChatException;
import com.mon.Chatserver.Exception.UserException;
import com.mon.Chatserver.model.Chat;
import com.mon.Chatserver.model.User;
import com.mon.Chatserver.request.GroupChatRequest;
import com.mon.Chatserver.request.SingleChatRequest;
import com.mon.Chatserver.response.ApiResponse;
import com.mon.Chatserver.service.ChatService;
import com.mon.Chatserver.service.UserService;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    private ChatService chatService;
    private UserService userService;
    
    public ChatController(ChatService chatService, UserService userService){
              this.chatService=chatService;
              this.userService=userService;
    }
    @PostMapping("/single")
    public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest,@RequestHeader("Authorization")String jwt) throws UserException{
          User reqUser =userService.findUserProfile(jwt);
          Chat chat=chatService.creatChat(reqUser, singleChatRequest.getUserId());

        return new ResponseEntity<Chat>(chat,HttpStatus.OK);

    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupChatRequest groupChatRequest,@RequestHeader("Authorization")String jwt) throws UserException{
          User reqUser =userService.findUserProfile(jwt);
          Chat chat=chatService.createGroup(groupChatRequest, reqUser);

        return new ResponseEntity<Chat>(chat,HttpStatus.OK);

    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatByIdHandler(@PathVariable Integer chatId,@RequestHeader("Authorization")String jwt) throws ChatException, UserException{
          
          Chat chat=chatService.findChatById(chatId);

        return new ResponseEntity<Chat>(chat,HttpStatus.OK);

    }

    @GetMapping("/user")
    public ResponseEntity<List<Chat>> findAllChatByUserIdHandler(@RequestHeader("Authorization")String jwt) throws ChatException, UserException{
        User reqUser =userService.findUserProfile(jwt);
          List<Chat> chats=chatService.findAllChatByUserId(reqUser.getId());

        return new ResponseEntity<List<Chat>>(chats,HttpStatus.OK);

    }

    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable Integer chatId,@PathVariable Integer userId,@RequestHeader("Authorization")String jwt) throws ChatException, UserException{
        User reqUser =userService.findUserProfile(jwt);
        Chat chat =chatService.addUserToGroup(userId,chatId,reqUser);

        return new ResponseEntity<Chat>(chat,HttpStatus.OK);

    }

    @PutMapping("/{userId}/add/{chatId}")
    public ResponseEntity<Chat> removeUserFromGroup(@PathVariable Integer userId,@PathVariable Integer chatId,@RequestHeader("Authorization")String jwt) throws ChatException, UserException{
        User reqUser =userService.findUserProfile(jwt);
        Chat chat =chatService.removeFromGroup(chatId,userId,reqUser);

        return new ResponseEntity<Chat>(chat,HttpStatus.OK);
    }


    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Integer chatId,@RequestHeader("Authorization")String jwt) throws ChatException, UserException{
        User reqUser =userService.findUserProfile(jwt);
        chatService.deleteChat(chatId,reqUser.getId());
        ApiResponse res= new ApiResponse("Chat is deleted successfully", true);
        return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);

    }





    
}
