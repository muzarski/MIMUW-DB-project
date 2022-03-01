package com.mimuw.games.entity.ranking.utils;

public enum Stat {
    None("None"),
    MinDurationWin("Win with minimal duration"), 
    AvgDurationWin("Average duration of the won match"), 
    AvgDuration("Average duration of the match"), 
    AvgPoints("Average of the points scored in the matches"),
    SumPoints("Sum of the points scored in the matches"),
    SumDuration("Sum of the durations of the matches"),
    SumWins("Number of the won matches");
    
    private final String displayValue;
    
    private Stat(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }
}
