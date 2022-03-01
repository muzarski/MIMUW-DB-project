package com.mimuw.games.config;

import com.mimuw.games.dao.PlayerRepository;
import com.mimuw.games.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
    private final PlayerRepository playerRepository;

    @Autowired
    public MyAuthenticationSuccessHandler(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
            throws IOException, ServletException {
        
        String nickname = authentication.getName();
        Player player = playerRepository.findByNickname(nickname);

        HttpSession session = request.getSession();
        session.setAttribute("player", player);
        
        response.sendRedirect("/");
    }
}
