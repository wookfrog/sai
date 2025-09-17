package com.project42.sai.invite;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project42.sai.dto.InviteCodeDto;

@Mapper
public interface InviteSqlMapper {
    // 초대코드 insert
    void insertInviteCode(InviteCodeDto inviteCodeDto);
    // 사용하지 않은 초대 코드 조회
    InviteCodeDto selectLatestUnusedInviteCodeByUserId(Long userId);
    // 초대코드로 정보 조회
    InviteCodeDto selectInviteCodeByCode(String code);
    // 초대코드 사용처리 + 커플 매핑
    void updateInviteCodeAsUsed(@Param("code") String code, @Param("coupleId") Long coupleId);
}
