package com.mimuw.games.controller;

import com.mimuw.games.entity.Player;
import com.mimuw.games.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    
    private final PlayerService playerService;

    @Autowired
    public RegistrationController(PlayerService playerService) {
        this.playerService = playerService;
    }
    
    @RequestMapping("/registrationForm")
    public String registrationForm(Model model) {
        model.addAttribute("player", new Player());
        return "register";
    }
    
    @PostMapping("/processRegistration")
    public String processRegistration(@ModelAttribute("player") Player player, Model model) {
        
        Player dbPlayer = playerService.findByNickname(player.getNickname());
        
        if (dbPlayer != null) {
            model.addAttribute("player", new Player());
            model.addAttribute("regError", "User with given nickname already exists.");
            
            return "register";
        }
        
        playerService.save(player);
        return "register-confirmation";
    }
}
