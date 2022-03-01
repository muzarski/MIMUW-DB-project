package com.mimuw.games.service;

import com.mimuw.games.dao.GameRepository;
import com.mimuw.games.dao.PlayerRepository;
import com.mimuw.games.dao.RoleRepository;
import com.mimuw.games.entity.Player;
import com.mimuw.games.entity.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    
    private final PlayerRepository playerRepository;
    private final RoleRepository roleRepository;
    private final GameRepository gameRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, RoleRepository roleRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.roleRepository = roleRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public Player findByNickname(String nickname) {
        return playerRepository.findByNickname(nickname);
    }

    @Override
    public void save(Player player) {
        player.setId(0);
        player.setPassword("{noop}" + player.getPassword());
        player.addRole(roleRepository.findByName("ROLE_PLAYER"));
        player.initRank(gameRepository.findById(1).orElseThrow());
        playerRepository.save(player);
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Player player = playerRepository.findByNickname(username);
        
        if (player == null) {
            throw new UsernameNotFoundException("Invalid nickname or password");
        }
        
        return new User(player.getNickname(), player.getPassword(), getAuthorities(player.getRoles()));
    }
    

}
