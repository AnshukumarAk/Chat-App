package com.anshu.chatapp.Models;

public class StatusModel {
    String uId,userName,message,messageId;
    Long timestamp;

    public StatusModel(String uId, String userName, String message, String messageId, Long timestamp) {
        this.uId = uId;
        this.userName = userName;
        this.message = message;
        this.messageId = messageId;
        this.timestamp = timestamp;
    }

    public StatusModel(){}

    public StatusModel(String uId, String userName,String message) {
        this.uId = uId;
        this.userName = userName;
        this.message = message;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
