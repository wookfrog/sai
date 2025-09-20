package com.project42.sai.chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project42.sai.couple.CoupleService;
import com.project42.sai.dto.ChatDto;
import com.project42.sai.dto.CoupleDto;
import com.project42.sai.dto.UserDto;
import com.project42.sai.notification.NotificationService;
import com.project42.sai.util.SessionUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRestController {

    private final ChatService chatService;
    private final CoupleService coupleService;
    private final NotificationService notificationService;

    // 채팅 전송
    // * 1) DB에 채팅 저장
    // * 2) 상대방에게 알림 기록 + 실시간 푸시
    @PostMapping("/send")
    public void send(@RequestBody ChatDto chatDto, HttpSession session) {
        UserDto sender = SessionUtil.getLoginUser(session);
        // 보내는 사람 id 저장
        chatDto.setSenderUserId(sender.getId());
        //채팅 저장
        chatService.saveMessage(chatDto);
        // 알림 생성 및 전송
        CoupleDto couple = coupleService.getCoupleById(chatDto.getCoupleId());
        if (couple == null) {
            throw new IllegalArgumentException("유효하지 않은 coupleId 입니다.");
        }
        Long receiverId = couple.getMaleUserId().equals(sender.getId()) ? couple.getFemaleUserId() : couple.getMaleUserId();
        String senderNickname = sender.getNickname();
        String preview = chatDto.getContent() != null ? chatDto.getContent() : "";
        if (preview.length() > 20) {
            preview = preview.substring(0, 20) + "...";
        }

        String title = senderNickname + "님의 메시지";
        String message = preview;

        notificationService.createAndPush(
                receiverId,
                "CHAT",
                title,
                message,
                chatDto.getCoupleId(),
                "/chat/room/" + chatDto.getCoupleId());

    }

    // 유저정보 요청
    @GetMapping("/info")
    public Map<String, Object> getChatInfo(HttpSession session) {
        UserDto loginUser = SessionUtil.getLoginUser(session);
        CoupleDto couple = coupleService.getCoupleByUserId(loginUser.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("userId", loginUser.getId());
        result.put("nickname", loginUser.getNickname());
        result.put("coupleId", couple != null ? couple.getId() : null);

        return result;

    }

    // 유저 채팅 리스트
    @GetMapping("/history/{coupleId}")
    public List<ChatDto> getHistory(@PathVariable("coupleId") Long coupleId) {
        return chatService.getChatHistory(coupleId);
    }

    // 채팅 읽음 여부
    @PostMapping("/read/{coupleId}")
    public void markAsRead(@PathVariable("coupleId") Long coupleId, HttpSession session) {
        UserDto loginUser = SessionUtil.getLoginUser(session);
        chatService.markMessagesAsRead(coupleId, loginUser.getId());
    }

}
