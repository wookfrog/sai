package com.project42.sai.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project42.sai.dto.UserDto;

@Service
public class UserService {

    @Autowired
    UserSqlMapper userSqlMapper;

    // 유저 로그인
    public UserDto login(String loginId, String password) {
        UserDto user = userSqlMapper.selectUserByLoginId(loginId);
        if (user != null && user.getPassword().equals(password) && "local".equals(user.getProvider())) {
            return user;
        }
        return null;
    }

    // // 관리자 로그인
    // public AdminDto adminLogin(String adminEmail, String adminPassword){
    //     AdminDto admin = userSqlMapper.selectAdminByLoginId(adminEmail);
    //     if(admin != null && admin.getAdminPassword().equals(adminPassword)){
    //         return admin;
    //     }
    //     return null;
    // }
    // 일반 회원 가입
    public void userRegister(UserDto userDto) {
        if (userDto.getProvider() == null) {
            userDto.setProvider("local");
        }
        userSqlMapper.insertUser(userDto);
    }

    // sns 회원가입
    public UserDto processSocialUser(String provider, String providerId, String email, String nickname) {
        UserDto existingUser = userSqlMapper.selectUserByProvider(provider, providerId);

        if (existingUser != null) {
            return existingUser;
        }

        UserDto newUser = new UserDto();
        newUser.setLoginId(email);
        newUser.setPassword(null);
        newUser.setNickname(nickname);
        newUser.setProviderId(providerId);
        newUser.setProvider(provider);
        userSqlMapper.insertUser(newUser);

        // insert 후 select로 다시 받아서 return (id 포함)
        return userSqlMapper.selectUserByProvider(provider, providerId);
    }

    // 커플 매칭 시 성별 업데이트
     public void updateGender(Long userId, String gender){
        userSqlMapper.updateGender(userId, gender);
     }

    //  커플 매칭 전 개인정보 업데이트
    public void updateUserInfo(UserDto userDto){
        userSqlMapper.updateUserInfo(userDto);
    }
     
}
