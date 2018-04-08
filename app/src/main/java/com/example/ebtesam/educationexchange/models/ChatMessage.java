package com.example.ebtesam.educationexchange.models;

/**
 * Created by reale on 18/11/2016.
 */

public class ChatMessage {public String sender;
    public String receiver;
    public String senderUid;
    public String receiverUid;
    public String message;
    public long timestamp;

    public ChatMessage() {
    }

    public ChatMessage(String sender, String receiver, String senderUid, String receiverUid, String message, long timestamp) {

        this.sender = sender;
        this.receiver = receiver;
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
        this.message = message;
        this.timestamp = timestamp;
    }
}
