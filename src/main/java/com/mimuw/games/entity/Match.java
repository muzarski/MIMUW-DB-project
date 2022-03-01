package com.mimuw.games.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "pmatch")
public class Match {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Game game;
    
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Performance> performances;
    
    @Column(name = "duration")
    private int duration;
    
    @Column(name = "match_date")
    private Timestamp date;

    public Match() {
        
    }

    public Match(int id, Game game, List<Performance> performances, int duration, Timestamp date) {
        this.id = id;
        this.game = game;
        this.performances = performances;
        this.duration = duration;
        this.date = date;
    }

    public String timeAgo() {
        long milliSecPerMinute = 60 * 1000;
        long milliSecPerHour = milliSecPerMinute * 60;
        long milliSecPerDay = milliSecPerHour * 24;
        long milliSecPerMonth = milliSecPerDay * 30;
        long milliSecPerYear = milliSecPerDay * 365;

        long msExpired = System.currentTimeMillis() - this.date.getTime();
        
        if (msExpired < milliSecPerMinute) {
            if (msExpired / 1000 == 1) {
                return msExpired / 1000 + " second ago";
            } else {
                return msExpired / 1000 + " seconds ago";
            }
        }
        else if (msExpired < milliSecPerHour) {
            if (msExpired / milliSecPerMinute == 1) {
                return msExpired / milliSecPerMinute + " minute ago";
            } else {
                return msExpired / milliSecPerMinute + " minutes ago";
            }
        }
        else if (msExpired < milliSecPerDay) {
            if (msExpired / milliSecPerHour == 1) {
                return msExpired / milliSecPerHour + " hour ago";
            } else {
                return msExpired / milliSecPerHour + " hours ago";
            }
        }
        else if (msExpired < milliSecPerMonth) {
            if (msExpired / milliSecPerDay == 1) {
                return msExpired / milliSecPerDay + " day ago";
            } else {
                return msExpired / milliSecPerDay + " days ago";
            }
        }
        else if (msExpired < milliSecPerYear) {
            if (msExpired / milliSecPerMonth == 1) {
                return msExpired / milliSecPerMonth + "  month ago";
            } else {
                return msExpired / milliSecPerMonth + "  months ago";
            }
        }
        else {
            if (msExpired / milliSecPerYear == 1) {
                return msExpired / milliSecPerYear + " year ago";
            } else {
                return msExpired / milliSecPerYear + " years ago";
            }
        }
    }
    
    public String getDateNoSecondsAndMillis() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);

        Date resDate = new Date(c.getTimeInMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm", Locale.ENGLISH);
        return sdf.format(resDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
