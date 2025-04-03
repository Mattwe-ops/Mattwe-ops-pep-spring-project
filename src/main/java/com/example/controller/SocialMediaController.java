package com.example.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    /*app.get("example-endpoint", this::exampleHandler);
    app.post("/register", this::postRegistrationHandler);
    app.post("/login", this::postLoginHandler);
    app.post("/messages", this::postMessageHandler);
    app.get("/messages", this::getAllMessagesHandler);
    app.get("/messages/{message_id}", this::getMessageByIdHandler);
    app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
    app.patch("/messages/{message_id}", this::patchUpdateMessageHandler);
    app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserHandler);*/

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account){
        Optional<Account> tempAccountA = accountService.findByAccountUsername(account.getUsername());
        if(tempAccountA.isPresent()){
            return ResponseEntity.status(409).body(null);
        }
        Account tempAccountB = accountService.register(account);
        if(tempAccountB.equals(null)){
            return ResponseEntity.status(400).body(null);
        } else {
            return ResponseEntity.status(200).body(tempAccountB);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account){
        Optional<Account> tempAccount = accountService.login(account);
        if(tempAccount.isPresent()){
            return ResponseEntity.status(401).body(null);
        } else {
            return ResponseEntity.status(200).body(tempAccount.get());
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message){
        Message tempMessage = messageService.addMessage(message);
        if(tempMessage.equals(null)){
            return ResponseEntity.status(400).body(null);
        } else {
            return ResponseEntity.status(200).body(tempMessage);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable long message_id){
        return ResponseEntity.status(200).body(messageService.getMessageById(message_id));
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable long message_id){
        return ResponseEntity.status(200).body(messageService.deleteMessage(message_id));
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable long message_id, @RequestBody Message message){
        Message tempMessage = messageService.updateMessage(message_id, message);
        if(tempMessage.equals(null)){
            return ResponseEntity.status(400).body(0);
        } else {
            return ResponseEntity.status(200).body(1);
        }
        //return ResponseEntity.status(200).body(messageService.deleteMessage(message_id));
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable long account_id){
        return ResponseEntity.status(200).body(messageService.getAllMessagesByUser(account_id));
    }
}
