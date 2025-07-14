package com.aloha.magicpos.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.aloha.magicpos.domain.CustomUser;
import com.aloha.magicpos.domain.Users;
import com.aloha.magicpos.mapper.UserTicketMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그인 성공 처리 이벤트 핸들러
 */
@Slf4j
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserTicketMapper userTicketMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        log.info("✅ 로그인 성공!");

        // 🍪 아이디 저장 여부 확인
        String rememberId = request.getParameter("remember-id");
        String username = request.getParameter("id");  // name="id"인 input에서 받아옴

        // ✅ 쿠키 처리
        Cookie cookie = new Cookie("remember-id", username);
        cookie.setPath("/");

        if ("on".equals(rememberId)) {
            cookie.setMaxAge(60 * 60 * 24 * 7); // 7일
        } else {
            cookie.setMaxAge(0); // 쿠키 삭제
        }

        response.addCookie(cookie);

        // ✅ 권한에 따라 이동할 페이지 설정
        String redirectUrl = "/";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean isAdmin = false;
        boolean isUser = false;

        for (GrantedAuthority auth : authorities) {
            if (auth.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
            } else if (auth.getAuthority().equals("ROLE_USER")) {
                isUser = true;
            }
        }

        if (isAdmin) {
            redirectUrl = "/admin";
        } else if (isUser) {
            CustomUser customUser = (CustomUser) authentication.getPrincipal();
            Users user = customUser.getUser();

            // int remainingTime = userTicketMapper.findRemainTimeByUserNo(user.getNo());
            Integer remain = userTicketMapper.findRemainTimeByUserNo(user.getNo());
            int remainingTime = (remain != null) ? remain : 0;
            log.info("🎫 남은 시간: {}분", remainingTime);

            if (remainingTime <= 0) {
                redirectUrl = "/ticket?message=noTime";
            } else {
                redirectUrl = "/menu";
            }
        }

        // 최종 리다이렉트
        response.sendRedirect(redirectUrl);
    }
}
