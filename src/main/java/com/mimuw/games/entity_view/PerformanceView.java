package com.mimuw.games.entity_view;

import com.mimuw.games.entity.Game;
import com.mimuw.games.entity.Player;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.*;

@Entity
@Subselect("""
        SELECT
            p.id AS id,
            p.player_id AS player_id,
            m.game_id AS game_id,
            p.score AS score,
            p.position AS position,
            m.duration AS duration
        FROM
            performance p, pmatch m
        WHERE
            m.id = p.match_id
""")
@Immutable
@Synchronize({"performance", "pmatch"})
public class PerformanceView {
    
    @Id
    @Column(name = "id")
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
    
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
    
    @Column(name = "score")
    private int score;
    
    @Column(name = "position")
    private int position;

    @Column(name = "duration")
    private int duration;

    public PerformanceView() {
        
    }

    public PerformanceView(int id, Player player, Game game, int score, int position, int duration) {
        this.id = id;
        this.player = player;
        this.game = game;
        this.score = score;
        this.position = position;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "PerformanceView{" +
                "id=" + id +
                ", player=" + player +
                ", game=" + game +
                ", score=" + score +
                ", position=" + position +
                ", duration=" + duration +
                '}';
    }
}
