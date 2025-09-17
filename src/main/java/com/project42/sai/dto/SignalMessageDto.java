package com.project42.sai.dto;

import lombok.Data;

@Data
public class SignalMessageDto {
    private String type;      // offer, answer, ice
    private String senderId;  // 보내는 사용자 ID (세션 또는 유저 기준)
    private Object data;      // SDP 또는 ICE 정보
}
