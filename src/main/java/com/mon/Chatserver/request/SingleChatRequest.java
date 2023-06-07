package com.mon.Chatserver.request;

import lombok.Data;

@Data
public class SingleChatRequest {
    private Integer userId;

    public SingleChatRequest(Integer userId) {
        this.userId=userId;
    }
}
