package com.mimuw.games.entity.ranking.utils;

import com.mimuw.games.entity.ranking.Ranking;

import java.util.ArrayList;
import java.util.List;

public class RankingUtils {

    private static String statToColumn(Stat stat) {
        switch(stat) {
            case MinDurationWin: return "MinWinDuration";
            case AvgDuration: return "AvgMatchDuration";
            case AvgDurationWin: return "AvgWinDuration";
            case AvgPoints: return "AvgScore";
            case SumPoints: return "SumPoints";
            case SumDuration: return "SumDuration";
            case SumWins: return "Won";
            default: return "";
        }
    }
    
    public static List<String> generateColumns(Ranking ranking) {
        List<String> columns = new ArrayList<>();
        if (ranking.getStat1() != Stat.None) {
            columns.add(statToColumn(ranking.getStat1()));
        }
        if (ranking.getStat2() != Stat.None) {
            columns.add(statToColumn(ranking.getStat2()));
        }
        if (ranking.getStat3() != Stat.None) {
            columns.add(statToColumn(ranking.getStat3()));
        }
        return columns;
    }


    private static String selectColumn(Stat stat) {
        switch (stat) {
            case MinDurationWin: return "min(pv.duration)";
            case AvgDurationWin: case AvgDuration:
                return "avg(pv.duration)";
            case AvgPoints: return "avg(pv.score)";
            case SumPoints: return "sum(pv.score)";
            case SumDuration: return "sum(pv.duration)";
            case SumWins: return "sum(case when pv.position = 1 then 1 else 0 end)";
            default: return "";
        }
    }

    private static String selectClause(Ranking ranking) {
        StringBuilder select = new StringBuilder("select pv.player.id, pv.player.nickname");
        
        int statNumber = 1;
        if (ranking.getStat1() != Stat.None) {
            select.append(", ")
                  .append(selectColumn(ranking.getStat1()))
                  .append(" as stat")
                  .append(statNumber);
            statNumber++;
        }
        
        if (ranking.getStat2() != Stat.None) {
            select.append(", ")
                    .append(selectColumn(ranking.getStat2()))
                    .append(" as stat")
                    .append(statNumber);
            statNumber++;
        }
        
        if (ranking.getStat3() != Stat.None) {
            select.append(", ")
                    .append(selectColumn(ranking.getStat3()))
                    .append(" as stat")
                    .append(statNumber);
        }
        
        select.append(" ");
        return select.toString();
    }


    private static String fromClause() {
        return "from PerformanceView pv ";
    }
    
    private static boolean useWhere(Stat stat) {
        switch(stat) {
            case MinDurationWin: case AvgDuration: return true;
            default: return false;
        }
    }

    private static String whereClause(Ranking ranking) {
        if (ranking.getGame().getId() != 1 || useWhere(ranking.getStat1())
            || useWhere(ranking.getStat2()) || useWhere(ranking.getStat3())) {
            
            boolean and = false;
            StringBuilder where = new StringBuilder("where ");
            
            if (ranking.getGame().getId() != 1) {
                where.append("pv.game =: myGame");
                and = true;
            }
            
            if (useWhere(ranking.getStat1()) || useWhere(ranking.getStat2()) || useWhere(ranking.getStat3())) {
                if (and) {
                    where.append(" and");
                }
                
                where.append(" pv.position = 1");
            }
            
            where.append(" ");
            return where.toString();
        }
        return "";
    }

    private static String groupByClause() {
        return "group by pv.player.id, pv.player.nickname";
    }

    public static String generateQuery(Ranking ranking) {
        return selectClause(ranking) +
               fromClause() +
               whereClause(ranking) +
               groupByClause();
    }
}
