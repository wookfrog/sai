package com.project42.sai.notification;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project42.sai.dto.NotificationDto;
import com.project42.sai.util.SessionUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationRestController {
    private final NotificationMapper notificationMapper;
     /**
     * 최근 알림 목록 조회
     * - 기본은 20개
     * - 프론트에서 뱃지/드롭다운 채우기 용도로 사용
     */
    @GetMapping
    public List<NotificationDto>list(HttpSession session, @RequestParam(defaultValue="20") int limit){
        Long userId = SessionUtil.getLoginUserId(session);
        
        return notificationMapper.selectRecent(userId, limit);
    }

     /**
     * 알림 읽음 처리
     * - 사용자가 알림을 클릭했을 때 호출
     * - is_read = true, read_at = NOW()
     */
    @PostMapping("/{id}/read")
    public void markRead(@PathVariable Long id, HttpSession session){
        Long userId = SessionUtil.getLoginUserId(session);
        notificationMapper.markRead(userId, id);
    }
}
