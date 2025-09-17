package com.project42.sai.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ChatDto {

    private Long id;                  // 메시지 고유 ID
    private Long coupleId;           // 커플 ID
    private Long senderUserId;       // 보낸 사용자 ID
    private String senderNickname;   // 보낸 사용자 닉네임

    private String messageType;      // "TEXT" 또는 "IMAGE"
    private String content;          // 텍스트 메시지 내용
    private String imageUrl;         // 이미지 메시지 URL

    private Boolean isRead;          // 읽음 여부
    private LocalDateTime sentAt;    // 메시지 전송 시간
}
