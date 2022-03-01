package com.mimuw.games.dao;

import com.mimuw.games.entity.ranking.Ranking;
import org.springframework.data.repository.CrudRepository;

public interface RankingRepository extends CrudRepository<Ranking, Integer> {
    
}
