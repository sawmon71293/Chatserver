package com.mon.Chatserver.request;

public class SendMessageRequest {
    private Integer userId;
    private Integer chatId;
    private String content;

    public SendMessageRequest() {
       
    }

    public SendMessageRequest(Integer chatId, Integer userId, String content) {
        super();
        this.chatId = chatId;
        this.userId = userId;
        this.content = content;
    }

    /**
     * @return Integer return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return Integer return the chatId
     */
    public Integer getChatId() {
        return chatId;
    }

    /**
     * @param chatId the chatId to set
     */
    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    /**
     * @return String return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

}
