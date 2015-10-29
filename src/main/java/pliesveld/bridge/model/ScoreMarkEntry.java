package pliesveld.bridge.model;

import pliesveld.bridge.model.ScoreMark;
import pliesveld.bridge.model.Team;

import java.io.Serializable;

public class ScoreMarkEntry
    implements Serializable
{
    private Team team;
    private ScoreMark mark;
    private int points;

    public ScoreMarkEntry(Team team, ScoreMark mark, int points)
    {
        this.team = team;
        this.mark = mark;
        this.points = points;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public ScoreMark getMark() {
        return mark;
    }

    public void setMark(ScoreMark mark) {
        this.mark = mark;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}