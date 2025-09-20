package com.project42.sai.notification;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project42.sai.dto.NotificationDto;
import com.project42.sai.util.SessionUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationPageController {

    private final NotificationMapper notificationMapper;

    @GetMapping("/list")
    public String listPage(HttpSession session, Model model){
        Long userId = SessionUtil.getLoginUserId(session);
        if(userId == null){
            return "redirect:/login";
        }
        List<NotificationDto> notifications = notificationMapper.selectRecent(userId,100);
        model.addAttribute("notifications", notifications);
        return "notifications/list";
    }
}
