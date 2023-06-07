package com.mon.Chatserver.service;

import java.util.List;

import com.mon.Chatserver.Exception.ChatException;
import com.mon.Chatserver.Exception.MessageException;
import com.mon.Chatserver.Exception.UserException;
import com.mon.Chatserver.model.Message;
import com.mon.Chatserver.model.User;
import com.mon.Chatserver.request.SendMessageRequest;

public interface MessageService {

    public Message sendMessage(SendMessageRequest req) throws UserException,ChatException;
    public List<Message> getChatsMessages(Integer chatId,User reqUser) throws ChatException,UserException;
    public Message findMessageById(Integer messageId) throws MessageException;
    public void deleteMessage(Integer messageId,User reqUser) throws UserException;
    
    
}
