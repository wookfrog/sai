package com.project42.sai.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project42.sai.dto.UserDto;
import com.project42.sai.util.SessionUtil;

import jakarta.servlet.http.HttpSession;

//	사용자 전반 기능 
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    // 회원가입 폼
    @GetMapping("/register")
    public String registerForm() {
        return "user/registerPage";
    }
    // 회원가입 처리

    @PostMapping("/register")
    public String register(UserDto userDto) {
        userService.userRegister(userDto);
        return "redirect:/user/register/complete";
    }

    // 회원가입 완료
    @GetMapping("/register/complete")
    public String registerCompletePage() {
        return "user/registerComplete"; // templates/user/registerComplete.html
    }

    // user 개인 정보 입력
    @PostMapping("/setup")
    public String setupUserInfo(UserDto userDto, HttpSession session) {
        UserDto loginUser = SessionUtil.getLoginUser(session);

        userDto.setId(loginUser.getId());

        loginUser.setGender(userDto.getGender());
        loginUser.setBirth(userDto.getBirth());
        loginUser.setNickname(userDto.getNickname());
        
        userService.updateUserInfo(userDto);

        return "redirect:/invite";
    }

    @GetMapping("/profileSetup")
    public String showProfileSetupPage() {
        return "user/profileSetup";
    }

}
