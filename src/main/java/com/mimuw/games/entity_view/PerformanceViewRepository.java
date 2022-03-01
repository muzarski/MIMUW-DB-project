package com.mimuw.games.entity_view;

import com.mimuw.games.entity.Game;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PerformanceViewRepository {
    
    private final EntityManager entityManager;

    @Autowired
    public PerformanceViewRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Object[]> customQuery(Game game, String queryStr) {
        Session session = entityManager.unwrap(Session.class);
        Query<Object[]> query = session.createQuery(queryStr, Object[].class);
        if (game.getId() != 1) {
            query.setParameter("myGame", game);
        }
        return query.getResultList();
    }
}
