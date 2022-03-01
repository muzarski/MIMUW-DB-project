package com.mimuw.games.entity;

import com.mimuw.games.entity.security.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "player")
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "nickname", unique = true)
    private String nickname;
    
    @Column(name = "password")
    private String password;
    
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyJoinColumn(name = "game_id")
    private Map<Game, Rank> ranks;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public Player() {
        this.ranks = new HashMap<>();
        this.roles = new ArrayList<>();
    }

    public Player(int id, String nickname, Map<Game, Rank> ranks) {
        this.id = id;
        this.nickname = nickname;
        this.ranks = ranks;
    }

    public Player(int id, String nickname, String password, Map<Game, Rank> ranks) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.ranks = ranks;
        this.roles = new ArrayList<>();
    }
    
    public void addRole(Role role) {
        roles.add(role);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Map<Game, Rank> getRanks() {
        return ranks;
    }

    public void setRanks(Map<Game, Rank> ranks) {
        this.ranks = ranks;
    }
    
    public void addRank(Game game, Rank rank) {
        this.ranks.put(game, rank);
    }
    
    public void initRank(Game game) {
        if (!this.ranks.containsKey(game)) {
            this.ranks.put(game, new Rank(0, this, game));
        }
    }
    
    public void updateRank(Game game, int points, int position) {
        initRank(game);
        
        Rank r = this.ranks.get(game);
        r.addPerformance(points, position);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
