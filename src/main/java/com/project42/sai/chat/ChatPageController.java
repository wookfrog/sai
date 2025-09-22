package com.project42.sai.chat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatPageController {

    @GetMapping("/room/{coupleId}")
    public String showChatPage(@PathVariable("coupleId") Long coupleId, Model model) {
        model.addAttribute("coupleId", coupleId);
        return "chat/chat";
    }

    @GetMapping("/room")
    public String showChatRoomDefault() {
        return "chat/chat"; // or redirect to home
    }
}
