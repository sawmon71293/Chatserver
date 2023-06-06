package com.mon.Chatserver.service;

import java.util.List;

import com.mon.Chatserver.Exception.ChatException;
import com.mon.Chatserver.Exception.UserException;
import com.mon.Chatserver.model.Chat;
import com.mon.Chatserver.model.User;
import com.mon.Chatserver.request.GroupChatRequest;

public interface ChatService {

    public Chat creatChat(User reqUser,Integer userId2) throws UserException;
    public Chat findChatById(Integer userId) throws ChatException;
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException;
    public Chat createGroup(GroupChatRequest req,User reqUser) throws UserException;
    public Chat addUserToGroup(Integer userId,Integer chatId, User reqUser) throws UserException, ChatException;
    public Chat renameGroup(Integer chatId,String groupName,User reqUser) throws UserException, ChatException;
    public Chat removeFromGroup(Integer chatId, Integer userd, User reqUser) throws UserException, ChatException;
    public void deleteChat(Integer chatId,Integer userId) throws ChatException, UserException;
    
}
