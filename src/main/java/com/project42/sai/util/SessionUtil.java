package com.project42.sai.util;

import org.springframework.stereotype.Component;

import com.project42.sai.dto.AdminDto;
import com.project42.sai.dto.UserDto;

import jakarta.servlet.http.HttpSession;
@Component
public class SessionUtil {


    // 로그인한 유저 정보를 세션에 저장 로직
    public static void setLoginUser(HttpSession session, UserDto userDto){
        session.setAttribute(SessionConstants.LOGIN_USER_ID, userDto.getId());
        session.setAttribute(SessionConstants.LOGIN_USER, userDto);
    }

    // 로그인한 관리자 정보를 세션에 저장
    public static void setLoginAdmin(HttpSession session, AdminDto adminDto){
        session.setAttribute(SessionConstants.LOGIN_ADMIN, adminDto);
    }

    // 로그인한 유저 정보를 세션에서 조회
    public static UserDto getLoginUser(HttpSession session){
        return (session != null) ? (UserDto)session.getAttribute(SessionConstants.LOGIN_USER): null;
    } 

    public static Long getLoginUserId(HttpSession session){
        return (session != null) ? (Long)session.getAttribute(SessionConstants.LOGIN_USER_ID) : null;
    }

    // 로그인 여부 확인
    public static boolean isLoggedIn(HttpSession session){
        return getLoginUser(session) != null;
    }

    //  로그아웃
    public static void logoutUser(HttpSession session){
        if(session != null){
            session.invalidate();
        }
    }

    // 세션에 커플 정보 저장
    public static void setCoupleId(HttpSession session, Long coupleId){
        session.setAttribute(SessionConstants.COUPLE_ID, coupleId);
    }

    // 커플 id 조회
    public static Long getCoupleId(HttpSession session){
        return (session != null) ? (Long)session.getAttribute(SessionConstants.COUPLE_ID) : null;
    }


}
