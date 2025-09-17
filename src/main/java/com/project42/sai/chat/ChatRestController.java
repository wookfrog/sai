package com.project42.sai.chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project42.sai.couple.CoupleService;
import com.project42.sai.dto.ChatDto;
import com.project42.sai.dto.CoupleDto;
import com.project42.sai.dto.UserDto;
import com.project42.sai.util.SessionUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRestController {

    private final ChatService chatService;
    private final CoupleService coupleService;

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
    public void markAsRead(@PathVariable Long coupleId, HttpSession session) {
        UserDto loginUser = SessionUtil.getLoginUser(session);
        chatService.markMessagesAsRead(coupleId, loginUser.getId());
    }

}
