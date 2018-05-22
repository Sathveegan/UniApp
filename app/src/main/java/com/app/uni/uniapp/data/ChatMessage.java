package com.app.uni.uniapp.data;

/**
 * Created by Sathveegan on 23-May-18.
 */

import java.util.Date;

public class ChatMessage {
    private String messageText;
    private String messageUser;
    private long messageTimeDate;

    public ChatMessage(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;

        messageTimeDate = new Date().getTime();
    }

    public ChatMessage() {
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTimeDate() {
        return messageTimeDate;
    }

    public void setMessageTimeDate(long messageTimeDate) {
        this.messageTimeDate = messageTimeDate;
    }
}
