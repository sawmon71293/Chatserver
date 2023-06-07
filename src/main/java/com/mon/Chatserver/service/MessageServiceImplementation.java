package com.mon.Chatserver.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.mon.Chatserver.Exception.ChatException;
import com.mon.Chatserver.Exception.MessageException;
import com.mon.Chatserver.Exception.UserException;
import com.mon.Chatserver.model.Chat;
import com.mon.Chatserver.model.Message;
import com.mon.Chatserver.model.User;
import com.mon.Chatserver.repository.MessageRepository;
import com.mon.Chatserver.request.SendMessageRequest;

public class MessageServiceImplementation implements MessageService {
    private MessageRepository messageRepository;
    private UserService userService;
    private ChatService chatService;

    public MessageServiceImplementation(MessageRepository messageRepository,UserService userService,ChatService chatService) {
       this.messageRepository=messageRepository;
       this.userService=userService;
       this.chatService=chatService;
    }
    @Override
    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
       User user =userService.findUserById(req.getUserId());
       Chat chat =chatService.findChatById(req.getChatId());
       Message message =new Message();
       message.setChat(chat);
       message.setUser(user);
       message.setContent(req.getContent());
       message.setTimeStamp(LocalDateTime.now());
        return message;
    }

    @Override
    public List<Message> getChatsMessages(Integer chatId,User reqUser) throws ChatException ,UserException{
        Chat chat =chatService.findChatById(chatId);
       if(!chat.getUsers().contains(reqUser)) {
         throw new UserException("You are not related to this chat");
       }
        List<Message> messages=messageRepository.findByChatId(chat.getId());

        return messages;
    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {
       Optional<Message> opt =messageRepository.findById(messageId);
       if(opt.isPresent())
       {
        return opt.get();
       }

       throw new MessageException("Message not found with id :"+ messageId);
    }

    @Override
    public void deleteMessage(Integer messageId,User reqUser) throws UserException {
       
       Optional<Message> message = messageRepository.findById(messageId);
       if(message.get().getUser().getId().equals(reqUser.getId())){
        messageRepository.deleteById(messageId);
       }
        throw new UserException("Deleting the message failed.");

    }
    
}
