package com.mon.Chatserver.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleChatRequest {
    private Integer userId;
    public SingleChatRequest(){}
    public SingleChatRequest(Integer userId) {
        this.userId=userId;
    }
}
