package com.mimuw.games.service;

import com.mimuw.games.entity.Player;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface PlayerService extends UserDetailsService {
    
    public Player findByNickname(String nickname);
    
    public void save(Player player);
}
