package com.mimuw.games.entity;

import javax.persistence.*;

@Entity
@Table(name = "prank")
public class Rank {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "points")
    private int points;
    
    @Column(name = "value")
    private String value;
    
    @Column(name = "wins")
    private int wins;

    @Column(name = "played")
    private int played;
    
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
    
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false, updatable = false)
    private Game game;

    public Rank(int id, Player player, Game game) {
        this.id = id;
        this.player = player;
        this.game = game;
        this.played = 0;
        this.wins = 0;
        this.points = 0;
        this.value = "NA";
    }

    public Rank() {
        
    }

    public Rank(int id, int points, String value, Player player, Game game) {
        this.id = id;
        this.points = points;
        this.value = value;
        this.player = player;
        this.game = game;
    }
    
    public void updateValue() {
        if (points == 0 && played == 0) {
            this.value = "NA";
        }
        else if (points < 51) {
            this.value = "Iron";
        }
        else if (points < 101) {
            this.value = "Bronze";
        }
        else if (points < 251) {
            this.value = "Silver";
        }
        else if (points < 501) {
            this.value = "Gold";
        }
        else if (points < 751) {
            this.value = "Platinum";
        }
        else if (points < 1251) {
            this.value = "Diamond";
        }
        else if (points < 2001) {
            this.value = "Master";
        }
        else if (points < 3001) {
            this.value = "Grandmaster";
        }
        else {
            this.value = "Challenger";
        }
    }
    
    public int getWinRatio() {
        return Math.round(100.f * wins / played);
    }
    
    public String getImageUrl() {
        return "/static/img/" + this.value + ".png";
    }
    
    public void addPerformance(int points, int position) {
        this.played++;
        if (position == 1)
            this.wins++;
        this.points = Math.max(0, this.points + points);
        updateValue();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    @Override
    public String toString() {
        return "Rank{" +
                "id=" + id +
                ", points=" + points +
                ", value='" + value + '\'' +
                ", wins=" + wins +
                ", played=" + played +
                ", player=" + player +
                ", game=" + game +
                '}';
    }
}
