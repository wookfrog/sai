package com.project42.sai.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatPageController {

    @GetMapping("/room")
    public String showChatController(){
        return "chat/chat";
    }
}
