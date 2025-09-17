package com.project42.sai.video;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.project42.sai.dto.SignalMessageDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SignalingController {
    private final SimpMessagingTemplate messagingTemplate;
    
    // offer 전송
    @MessageMapping("/offer/{roomId}")
    public void offer(@DestinationVariable String roomId, SignalMessageDto message){
        messagingTemplate.convertAndSend("/topic/signal" + roomId, message);
    }

    // answer 전송
    @MessageMapping("/answer/{roomId}")
    public void answer(@DestinationVariable String roomId, SignalMessageDto message){
        messagingTemplate.convertAndSend("/topic/signal/" + roomId, message);
    }
    
    // ice 전송
    @MessageMapping("/ice/{roomId}")
    public void iceCandidate(@DestinationVariable String roomId, SignalMessageDto message){
        messagingTemplate.convertAndSend("/topic/signal/" + roomId, message);
    }
}
