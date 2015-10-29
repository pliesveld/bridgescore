// vim: tabstop=4 shiftwidth=4 expandtab
package pliesveld.bridge.model;

import java.io.Serializable;
import java.util.EnumSet;
import java.lang.StringBuilder;
import java.util.LinkedList;
import java.util.List;

/**
 * Maintains the running score of the bridge game.
 */
public class BackScore implements Serializable
{
    final private int[] points_above_line = new int[2];
    final private BackScore.Game current_game = new BackScore.Game();
    final private EnumSet<Team> vulnerable = EnumSet.noneOf(Team.class);

    private static final int BOOK = 6;

    public int pointsTotal(Team team)
    {
        return points_above_line[team.ordinal()];
    }

    public int pointsPartial(Team team)
    {
        return current_game.partial(team);
    }

    public boolean isVulnerable(Team team)
    {
        return vulnerable.contains(team);
    }

    public static class ScoreMarksList extends LinkedList<ScoreMarkEntry>
    {
        void add(Team team, ScoreMark mark, int points)
        {
            ScoreMarkEntry new_entry = new ScoreMarkEntry(team,mark,points);
            add(new_entry);
        }
    }

    public List<ScoreMarkEntry> evaluateDeclarerPlay(AuctionContract contract, int tricks_taken_by_declarer)
    {
        Team declarer_team = contract.getDeclarer().team();
        Penalty penalty = contract.getPenalty();
        int level = contract.getLevel();

        int expected = BOOK + level;
        int made = tricks_taken_by_declarer - expected;

        boolean vulnerable = isVulnerable(declarer_team);

        ScoreMarksList scoreMarksList = new ScoreMarksList();

        if( made < 0 )
        { // Set by abs(made) under-tricks
            int points_bonus = 0;
            int undertricks = Math.abs(made);
            int undertrick_multiplier = 0;

            if(penalty == Penalty.UNDOUBLED)
            {
                undertrick_multiplier = 50;
                if(vulnerable)
                    undertrick_multiplier = 100;
            } else {
                /* first undertrick that is doubled
                    is worth 100 pts less */
                points_bonus = -100;

                if(!vulnerable)
                    undertrick_multiplier = 200;
                else
                    undertrick_multiplier = 300;

                /*
                    Each redoubled undertrick costs exactly twice the amount
                    of each doubled undertrick.
                 */
                if(penalty == Penalty.REDOUBLED)
                {
                    points_bonus = -200;
                    undertrick_multiplier *= 2;
                }
            }

            points_bonus += undertricks*undertrick_multiplier;
            add(declarer_team.other(),0,points_bonus,scoreMarksList);
            scoreMarksList.add(declarer_team.other(),ScoreMark.SET.UNDER_TRICKS,points_bonus);
        } else { // Contract + made over-tricks

            int points_below = 0;
            int points_bonus = 0;

            /* tricks bid and made */
            switch(contract.getSuit())
            {
                case NOTRUMP:
                    points_below = level*30 + 10;
                    break;
                case SPADES:
                case HEARTS:
                    points_below = level*30;
                    break;
                case DIAMONDS:
                case CLUBS:
                    points_below = level*20;
                    break;
            }

            int overtrick_multiplier = 0;

            /* penalty */
            switch(penalty)
            {
                case REDOUBLED:
                    points_below *= 2;
                    overtrick_multiplier += 100;
                    if(vulnerable)
                        overtrick_multiplier += 100;
                case DOUBLED:
                    points_bonus += 50;
                    scoreMarksList.add(declarer_team,ScoreMark.BONUS.INSULT,50);
                    points_below *= 2;
                    overtrick_multiplier += 100;
                    if(vulnerable)
                        overtrick_multiplier += 100;
                    break;
                case UNDOUBLED:
                    overtrick_multiplier = (contract.getSuit().isMinor() ? 20 : 30);
                    break;
            }

            scoreMarksList.add(declarer_team,ScoreMark.MADE.BID_AND_MADE,points_below);
            /* over tricks */
            points_bonus += made*overtrick_multiplier;

            if(made > 0)
            {
                scoreMarksList.add(declarer_team,ScoreMark.MADE.OVER_TRICKS,made*overtrick_multiplier);
            }

            /* small slam */
            if(level == 6)
            {
                int small_slam_pts = 0;
                if(!vulnerable)
                {
                    small_slam_pts = 500;
                } else {
                    small_slam_pts = 750;

                }
                scoreMarksList.add(declarer_team,ScoreMark.SLAM.SLAM_SMALL,small_slam_pts);
                points_bonus += small_slam_pts;
            } else if(level == 7) {
                /* grand slam */

                int grand_slam_pts = 0;
                if(!vulnerable)
                {
                    grand_slam_pts = 1000;
                } else {
                    grand_slam_pts = 1500;
                }
                scoreMarksList.add(declarer_team,ScoreMark.SLAM.SLAM_GRAND,grand_slam_pts);
                points_bonus += grand_slam_pts;
            }

            add(declarer_team,points_below,points_bonus,scoreMarksList);
        }
        return scoreMarksList;
    }

    public void clear() {
        points_above_line[0] = 0;
        points_above_line[1] = 0;
        current_game.clear();
        vulnerable.clear();
    }

    /*
        Maintains a partial score for each team.  A partial score
        is any game that has both teams' score below 100.
    */
    private class Game
        implements Serializable
    {
        final private int[] points_below_line = new int[2];

        void clear()
        {
            points_below_line[0] = 0;
            points_below_line[1] = 0;
        }

        int partial(Team team)
        {
            return points_below_line[team.ordinal()];
        }

        void add(Team team,int points_below)
        {
            assert isPartial();
            points_below_line[team.ordinal()] += points_below;
        }
        /*
            A partial game is defined to be one where no team
            has more than 100 pts
         */
        public boolean isPartial()
        {
            return points_below_line[0] < 100
                    && points_below_line[1] < 100;
        }

        /**
         * pre-condition: not a partial game
         * Returns the winner of this game.
         */
        Team winner()
        {
            for(Team t : Team.values())
            {
                if(points_below_line[t.ordinal()] >= 100)
                    return t;
            }
            return null;
        }

        @Override
        public boolean equals(Object other)
        {
            if(this == other) return true;
            if(other == null) return false;
            if(other instanceof BackScore.Game)
            {
                BackScore.Game rhs = (BackScore.Game)other;
                if(rhs.points_below_line[0] != rhs.points_below_line[0] ||
                        rhs.points_below_line[1] != rhs.points_below_line[1])
                    return false;

                return true;
            }
            return false;
        }
    }

    private void add(Team team, int points_below, int points_above, ScoreMarksList scoreMarksList)
    {
        points_above_line[team.ordinal()] += points_above;
        points_above_line[team.ordinal()] += points_below;
        current_game.add(team,points_below);

        if(!current_game.isPartial())
        {
            Team winner = current_game.winner();
            if(vulnerable.contains(winner))
            {
                //won rubber
                int rubber_points = 500;

                //won rubber in 2
                if(!vulnerable.contains(team.other()))
                    rubber_points = 700;


                scoreMarksList.add(team,ScoreMark.RUBBER.RUBBER_WIN,rubber_points);

                points_above_line[winner.ordinal()] += rubber_points;
                vulnerable.clear();
            } else {
                vulnerable.add(team);
            }
            current_game.clear();
        }

    }

    @Override
    public boolean equals(Object other)
    {
        if(this == other) return true;
        if(other == null) return false;
        if(other instanceof BackScore)
        {
            BackScore rhs = (BackScore)other;
            if(rhs.points_above_line[0] != rhs.points_above_line[0] ||
                    rhs.points_above_line[1] != rhs.points_above_line[1])
                return false;

            if(!current_game.equals(rhs.current_game))
                return false;

            if(!vulnerable.equals(rhs.vulnerable))
                return false;

            return true;
        }
        return false;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[BackScore");

        for(Team team : Team.values())
        {
            sb.append(" " + team + ":");
            sb.append(" score=" + pointsTotal(team));
            sb.append(" vuln=" + isVulnerable(team));
            sb.append(" partial=" + pointsPartial(team));
        }
        sb.append("]");

        return sb.toString();
    }
}
