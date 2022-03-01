package com.mimuw.games.entity;

import javax.persistence.*;

@Entity
@Table(name = "game")
public class Game {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "players_limit")
    private int playersLimit;
    
    @Column(name = "description")
    private String description;

    public Game() {
        
    }

    public Game(int id, String name, int playersLimit, String description) {
        this.id = id;
        this.name = name;
        this.playersLimit = playersLimit;
        this.description = description;
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

    public int getPlayersLimit() {
        return playersLimit;
    }

    public void setPlayersLimit(int playersLimit) {
        this.playersLimit = playersLimit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", playersLimit=" + playersLimit +
                ", description='" + description + '\'' +
                '}';
    }
}
