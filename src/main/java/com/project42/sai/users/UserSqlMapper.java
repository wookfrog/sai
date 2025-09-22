package com.project42.sai.users;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project42.sai.dto.AdminDto;
import com.project42.sai.dto.UserDto;

@Mapper
public interface UserSqlMapper {
    // 유저 로그인
    public UserDto selectUserByLoginId(String loginId);
    // 관리자 로그인 
    public AdminDto selectAdminByLoginId(String adminEmail);
    public void insertUser(UserDto userDto);
    public UserDto selectUserByProvider(@Param("provider") String provider, @Param("providerId") String providerId);
    public void updateGender(@Param("userId")Long userId, @Param("gender") String gender);
    public void updateUserInfo(UserDto userDto);
    public UserDto  selectUserById(@Param("id") Long id);
}

