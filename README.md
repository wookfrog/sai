# sai

로그인 성공 시:
SessionUtil.setLoginUser(session, userDto);

로그인 유저 정보 꺼내기:
UserDto user = SessionUtil.getLoginUser(session);
꺼낸 후에 원하는 필드를 조회
Long userId = user.getId();

로그인 유저 ID:
Long userId = SessionUtil.getLoginUserId(session);

로그인 여부:
if (!SessionUtil.isLoggedIn(session)) {
    return "redirect:/login";
}

로그아웃:
SessionUtil.logoutUser(session);