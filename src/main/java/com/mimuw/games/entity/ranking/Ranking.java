package com.mimuw.games.entity.ranking;

import com.mimuw.games.entity.Game;
import com.mimuw.games.entity.ranking.utils.Stat;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "ranking")
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "game_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Game game;
    
    @Column(name = "stat1")
    @Enumerated(EnumType.STRING)
    private Stat stat1;

    @Column(name = "stat2")
    @Enumerated(EnumType.STRING)
    private Stat stat2;

    @Column(name = "stat3")
    @Enumerated(EnumType.STRING)
    private Stat stat3;

    public Ranking() {
        
    }

    public Ranking(int id, String name, Game game, Stat stat1, Stat stat2, Stat stat3) {
        this.id = id;
        this.name = name;
        this.game = game;
        this.stat1 = stat1;
        this.stat2 = stat2;
        this.stat3 = stat3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Stat getStat1() {
        return stat1;
    }

    public void setStat1(Stat stat1) {
        this.stat1 = stat1;
    }

    public Stat getStat2() {
        return stat2;
    }

    public void setStat2(Stat stat2) {
        this.stat2 = stat2;
    }

    public Stat getStat3() {
        return stat3;
    }

    public void setStat3(Stat stat3) {
        this.stat3 = stat3;
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", game=" + game +
                ", stat1=" + stat1 +
                ", stat2=" + stat2 +
                ", stat3=" + stat3 +
                '}';
    }
}
