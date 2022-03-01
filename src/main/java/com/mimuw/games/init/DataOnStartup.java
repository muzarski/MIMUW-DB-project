package com.mimuw.games.init;

import com.mimuw.games.dao.GameRepository;
import com.mimuw.games.dao.MatchRepository;
import com.mimuw.games.dao.PlayerRepository;
import com.mimuw.games.dao.RoleRepository;
import com.mimuw.games.entity.Game;
import com.mimuw.games.entity.Match;
import com.mimuw.games.entity.Performance;
import com.mimuw.games.entity.Player;
import com.mimuw.games.entity.security.Role;
import com.mimuw.games.entity_view.PerformanceViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
public class DataOnStartup {
    
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PerformanceViewRepository performanceViewRepository;

    @Autowired
    public DataOnStartup(GameRepository gameRepository, PlayerRepository playerRepository, MatchRepository matchRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
    }
    
    public List<Game> generateGames() {
        List<Game> games = (List<Game>) gameRepository.findAll();
        if (games.isEmpty()) {
            games.add(new Game(0, "General", 1, "uwu1"));
            games.add(new Game(0, "Chess", 2, "uwu1"));
            games.add(new Game(0, "Scrabble", 4, "uwu2"));
            games.add(new Game(0, "LoL", 10, "uwu3"));
            games.add(new Game(0, "Bridge", 4, "uwu4"));
            games.add(new Game(0, "Backgamon", 2, "uwu5"));
            games.add(new Game(0, "Dota2", 10, "moba2"));
            
            gameRepository.saveAll(games);
        }
        
        return games;
    }
    
    public List<Player> generatePlayers(List<Role> roles) {
        List<Player> players = (List<Player>) playerRepository.findAll();
        
        if (players.isEmpty()) {
            Role r_player = roleRepository.findByName("ROLE_PLAYER");

            for (int i = 0; i < 100; ++i) {
                Player p = new Player(0, "player" + i, "{noop}player" + i, new HashMap<>());
                p.addRole(r_player);
                players.add(p);
            }
            
            playerRepository.saveAll(players);
        }
        
        return players;
    }
    
    public int getPoints(int playersCount, int position) {
        if (playersCount % 2 == 1 && position == (playersCount / 2 + 1)) {
            return 0;
        }
        else if (position <= playersCount / 2) {
            return 20 / position;
        }
        else {
            return -20 / (playersCount - position + 1);
        }
    }
     
    public void generateMatches(List<Game> games, List<Player> players, List<Role> roles) {
        List<Match> matches = (List<Match>) matchRepository.findAll();
        
        if (matches.isEmpty()) {
            long now = System.currentTimeMillis() - 2620743830L;

            int gameId, playersCount, points;
            Game game;
            Random r;
            for (int i = 0; i < 5000; ++i) {
                r = new Random();
                gameId = r.nextInt(games.size() - 1) + 1;
                game = games.get(gameId);
                playersCount = r.nextInt(game.getPlayersLimit() - 1) + 2;

                HashSet<Integer> mPlayers = new HashSet<>();
                while(mPlayers.size() < playersCount) {
                    mPlayers.add((r.nextInt(players.size()) + i) % players.size());
                }

                Match match = new Match(0, game, new ArrayList<>(), r.nextInt(40) + 20,
                        new Timestamp(now + 10000000 * i + r.nextInt(10000000)));
                List<Performance> performances = new ArrayList<>();

                int position = 1;
                for (int curPlayerId : mPlayers) {
                    Player curPlayer = players.get(curPlayerId);
                    points = getPoints(playersCount, position);
                    curPlayer.updateRank(game, points, position);
                    curPlayer.updateRank(games.get(0), points, position);
                    performances.add(new Performance(0, match, curPlayer, points, position));
                    ++position;
                }

                match.setPerformances(performances);
                matches.add(match);
            }

            playerRepository.saveAll(players);
            matchRepository.saveAll(matches);
        }
    }
    
    @EventListener
    public void populateDatabase(ContextRefreshedEvent event) {
        List<Role> roles = (List<Role>) roleRepository.findAll();
        if (roles.isEmpty()) {
            roles = Arrays.asList(new Role(0L, "ROLE_PLAYER"), new Role(0L, "ROLE_ADMIN"));
            roleRepository.saveAll(roles);
        }
        
        List<Game> games = generateGames();
        List <Player> players = generatePlayers((List<Role>) roleRepository.findAll());
        generateMatches(games, players, (List<Role>) roleRepository.findAll());
        
        Player admin = playerRepository.findByNickname("admin");
        if (admin == null) {
            admin = new Player(0, "admin", "{noop}admin", new HashMap<>());
            admin.addRole(roleRepository.findByName("ROLE_PLAYER"));
            admin.addRole(roleRepository.findByName("ROLE_ADMIN"));
            playerRepository.save(admin);
        }
    }
}
