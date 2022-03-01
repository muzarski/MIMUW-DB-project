package com.mimuw.games.controller;

import com.mimuw.games.dao.*;
import com.mimuw.games.entity.Game;
import com.mimuw.games.entity.Player;
import com.mimuw.games.entity.ranking.Ranking;
import com.mimuw.games.entity_view.PerformanceViewRepository;
import com.mimuw.games.entity.ranking.utils.RankingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class HomeController {
    
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final RankRepository rankRepository;
    private final MatchRepository matchRepository;
    private final RankingRepository rankingRepository;
    private final PerformanceViewRepository performanceViewRepository;

    @Autowired
    public HomeController(PlayerRepository playerRepository, GameRepository gameRepository,
                          RankRepository rankRepository, MatchRepository matchRepository,
                          RankingRepository rankingRepository, PerformanceViewRepository performanceViewRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.rankRepository = rankRepository;
        this.matchRepository = matchRepository;
        this.rankingRepository = rankingRepository;
        this.performanceViewRepository = performanceViewRepository;
    }

    @RequestMapping("/ranking")
    public String ranking(@RequestParam("game_id") Optional<Integer> gameId_, Model model) {
        int gameId = gameId_.orElse(1);
        
        model.addAttribute("ranks", rankRepository.findAllByGameIdList(gameId));

        Game game = gameRepository.findById(gameId).orElseThrow();
        model.addAttribute("game", game);
        
        model.addAttribute("games", gameRepository.findAll());
        model.addAttribute("rankings", rankingRepository.findAll());
        return "ranking";
    }
    
    @RequestMapping("/player")
    public String player(@RequestParam("player_id") int playerId, Model model) {
        model.addAttribute("games", gameRepository.findAll());
        
        Player player = playerRepository.findById(playerId).orElseThrow();
        model.addAttribute("player", player);
        
        model.addAttribute("matches", matchRepository.findAllByPlayer(player));
        model.addAttribute("rankings", rankingRepository.findAll());
        return "player";
    }
    
    @RequestMapping("/addGame")
    public String addGame(Model model) {
        model.addAttribute("new_game", new Game());
        model.addAttribute("games", gameRepository.findAll());
        model.addAttribute("rankings", rankingRepository.findAll());
        return "game-form";
    }
    
    @PostMapping("/saveGame")
    public String saveGame(@ModelAttribute Game game) {
        game.setId(0);
        gameRepository.save(game);
        return "redirect:/ranking";
    }
    
    @RequestMapping("/deleteGame")
    public String deleteGame(@RequestParam("game_id") int gameId) {
        rankRepository.deleteAllByGameId(gameId);
        gameRepository.deleteById(gameId);
        return "redirect:/ranking";
    }

    @RequestMapping("/deletePlayer")
    public String deletePlayer(@RequestParam("player_id") int playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow();
        
        if (!Objects.equals(player.getNickname(), SecurityContextHolder.getContext().getAuthentication().getName())) {
            return "/access-denied";
        }
        
        playerRepository.deleteById(playerId);
        return "redirect:/loginPage";
    }

    @RequestMapping("/customRanking")
    public String customRanking(@RequestParam("ranking_id") int rankingId, Model model) {
        Ranking ranking = rankingRepository.findById(rankingId).orElseThrow();
        List<String> columns = RankingUtils.generateColumns(ranking);
        String query = RankingUtils.generateQuery(ranking);
        List<Object[]> myRanking = performanceViewRepository.customQuery(ranking.getGame(), query);
        
        model.addAttribute("columns", columns);
        model.addAttribute("ranking_obj", ranking);
        model.addAttribute("games", gameRepository.findAll());
        model.addAttribute("rankings", rankingRepository.findAll());
        model.addAttribute("my_ranking", myRanking);
        return "custom-ranking";
    }
    
    @RequestMapping("/addRanking")
    public String addRanking(Model model) {
        model.addAttribute("new_ranking", new Ranking());
        model.addAttribute("games", gameRepository.findAll());
        model.addAttribute("rankings", rankingRepository.findAll());
        return "custom-ranking-form";
    }
    
    @PostMapping("/saveRanking")
    public String saveRanking(@ModelAttribute Ranking ranking) {
        ranking.setId(0);
        rankingRepository.save(ranking);
        return "redirect:/ranking";
    }
    
    @RequestMapping("/")
    public String home() {
        return "redirect:/ranking";
    }
}
