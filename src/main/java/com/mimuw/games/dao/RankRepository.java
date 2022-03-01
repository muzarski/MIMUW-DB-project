package com.mimuw.games.dao;

import com.mimuw.games.entity.Rank;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RankRepository extends PagingAndSortingRepository<Rank, Integer> {

    @Query("select r from Rank r where r.game.id = ?1 order by r.points desc")
    List<Rank> findAllByGameIdList(int gameId);
    
    @Modifying
    @Transactional
    @Query("delete from Rank r where r.game.id = ?1")
    void deleteAllByGameId(int gameId);
}
