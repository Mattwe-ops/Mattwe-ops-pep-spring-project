package com.example.service;

import java.util.*;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message addMessage(Message message){
        Optional<Account> tempAccount = accountRepository.findById(message.getPostedBy());
        if(!tempAccount.isPresent()){
            return null;
        } else if(message.getMessageText().isEmpty()){
            return null;
        } else if(message.getMessageText().length() >= 255){
            return null;
        } else {
            return messageRepository.save(message);
        }
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int message_id){
        Optional<Message> tempMessage = messageRepository.findById(message_id);
        if(tempMessage.isPresent()){
            return tempMessage.get();
        } else {
            return null;
        }
    }

    public Message deleteMessage(int message_id){
        Optional<Message> tempMessage = messageRepository.findById(message_id);
        if(tempMessage.isPresent()){
            return tempMessage.get();
        } else {
            return null;
        }
    }

    public Message updateMessage(int message_id, Message message){
        Optional<Message> tempMessage = messageRepository.findById(message_id);
        if(!tempMessage.isPresent()){
            return null;
        } else if(message.getMessageText().isEmpty()){
            return null;
        } else if(message.getMessageText().length() >= 255){
            return null;
        } else {
            messageRepository.save(message);
            return tempMessage.get();
        }
    }

    public List<Message> getAllMessagesByUser(int account_id){
        return messageRepository.findByPostedBy(account_id).get();
    }
}
