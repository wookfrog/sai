package com.project42.sai.invite;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.project42.sai.dto.InviteCodeDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InviteService {

    private final InviteSqlMapper inviteSqlMapper;

    //   랜덤 코드 생성 로직
    private String randomCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public InviteCodeDto getOrCreateInviteCode(Long userId) {
        // 코드를 사용했는지 확인
        InviteCodeDto existing  = inviteSqlMapper.selectLatestUnusedInviteCodeByUserId(userId);
        if (existing != null) {
            return existing ;
        }
        
        // 사용하지 않았으면 새 초대코드 발급
        InviteCodeDto newCode = new InviteCodeDto();
        newCode.setUserId(userId);
        newCode.setIsUsed(false);
        newCode.setCode(randomCode());
        newCode.setCreatedAt(LocalDateTime.now());

        inviteSqlMapper.insertInviteCode(newCode);

        return newCode;
    }

    // 초대코드 문자열로 해당 코드 조회
    public InviteCodeDto getInviteCode(String code) {
        return inviteSqlMapper.selectInviteCodeByCode(code);
    }

    // 커플 ID 연결
    public void markInviteCodeAsUsed(String code, Long userId) {
        inviteSqlMapper.updateInviteCodeAsUsed(code, userId);
    }
}
