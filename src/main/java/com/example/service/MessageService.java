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
        Optional<Account> tempAccount = accountRepository.findById((long)message.getPostedBy());
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

    public Message getMessageById(long message_id){
        return messageRepository.findById(message_id).get();
    }

    public int deleteMessage(long message_id){
        Optional<Message> tempMessage = messageRepository.findById(message_id);
        if(tempMessage.isPresent()){
            messageRepository.deleteById(message_id);
            return 1;
        } else {
            return 0;
        }
    }

    public Message updateMessage(long message_id, Message message){
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

    public List<Message> getAllMessagesByUser(long account_id){
        return messageRepository.findByPosted_by(account_id).get();
    }
}
