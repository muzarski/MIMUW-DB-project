package com.mimuw.games.dao;

import com.mimuw.games.entity.Match;
import com.mimuw.games.entity.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MatchRepository extends PagingAndSortingRepository<Match, Integer> {
    
    @Query("select m from Match m, Performance p where p.match = m and p.player = ?1")
    List<Match> findAllByPlayer(Player player);
}
