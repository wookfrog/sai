package com.project42.sai.notification;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project42.sai.dto.NotificationDto;

@Mapper
public interface NotificationMapper {
    // 알림기록
    void insert(NotificationDto notificationDto);
    // 최근 N개
    List<NotificationDto> selectRecent(@Param("userId") Long userId,@Param("limit") int limit);
    // 읽음 처리
    void markRead(@Param("userId") Long userId, @Param("id") Long id);
}
