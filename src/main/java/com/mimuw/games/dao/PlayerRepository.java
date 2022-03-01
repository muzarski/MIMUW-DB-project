package com.mimuw.games.dao;

import com.mimuw.games.entity.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
    
    @Query("select p from Player p where p.nickname = ?1")
    Player findByNickname(String nickname);
}
