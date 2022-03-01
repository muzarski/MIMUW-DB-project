package com.mimuw.games.dao;

import com.mimuw.games.entity.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Integer> {
    
}
