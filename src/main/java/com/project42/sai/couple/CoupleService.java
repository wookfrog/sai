package com.project42.sai.couple;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.project42.sai.dto.CoupleDto;
import com.project42.sai.dto.InviteCodeDto;
import com.project42.sai.invite.InviteService;
import com.project42.sai.users.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoupleService {
    private final CoupleSqlMapper coupleSqlMapper;
    private final InviteService inviteService; 
    private final UserService userService;
    

    // 유저 ID로 현재 커플이 있는지 확인
    public CoupleDto getCoupleByUserId(Long userId){
        return coupleSqlMapper.selectCoupleByUserId(userId);
    }

    // 초대코드를 통해 커플 매칭 처리
    public void matchCouple(String code, Long currentUserId, String gender){
        // 사용한 코드 조회
        InviteCodeDto inviteCode = inviteService.getInviteCode(code);
        //  코드가 사용 된 코드일 때 
        if(inviteCode == null || Boolean.TRUE.equals(inviteCode.getIsUsed())){
            throw new IllegalArgumentException("유효하지 않거나 이미 사용된 초대코드입니다.");
        }
        
        Long createId = inviteCode.getUserId();
        CoupleDto couple = new CoupleDto();
        if("MALE".equalsIgnoreCase(gender)){
            couple.setMaleUserId(currentUserId);
            couple.setFemaleUserId(createId);
        }else{
            couple.setMaleUserId(createId);
            couple.setFemaleUserId(currentUserId);
        }

        userService.updateGender(currentUserId, gender);
        
        couple.setStatus("ACTIVE");
        couple.setCreatedAt(LocalDateTime.now());
        couple.setUpdateAt(LocalDateTime.now());

        coupleSqlMapper.insertCouple(couple);

        inviteService.markInviteCodeAsUsed(code, couple.getId());
    }
}
