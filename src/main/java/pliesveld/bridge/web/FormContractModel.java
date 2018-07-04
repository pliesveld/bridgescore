package pliesveld.bridge.web;

import org.apache.wicket.util.io.IClusterable;


import pliesveld.bridge.model.Penalty;
import pliesveld.bridge.model.Seat;
import pliesveld.bridge.model.Suit;


/**
 * Represents a form model
 */

public final class FormContractModel implements IClusterable {

    private Seat seat = Seat.SOUTH;
    private Suit suit = Suit.CLUBS;
    private int level = 1;
    private Penalty penalty = Penalty.UNDOUBLED;
    private int tricks = 7;

    private String south = "SOUTHERN";

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(32);

        sb.append(getSeat());
        sb.append(' ');
        sb.append(getLevel());
        sb.append(getSuit());
        sb.append(" ");
        Penalty p = getPenalty();
        if(p != Penalty.UNDOUBLED)
            sb.append(p + " ");

        int expected = 6 + getLevel();
        int cmp = getTricks() - expected;

        if(cmp < 0)
        {
            sb.append("SET");
            int down = Math.abs(cmp);
            sb.append(" DOWN " + down);
        }
        else
        {
            sb.append("MADE");

            if(cmp > 0)
            {
                sb.append("+" + cmp + " OVER");
            }
        }

        return sb.toString();
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Penalty getPenalty() {
        return penalty;
    }

    public void setPenalty(Penalty penalty) {
        this.penalty = penalty;
    }

    public int getTricks() {
        return tricks;
    }

    public void setTricks(int tricks) {
        this.tricks = tricks;
    }

    public String getSouth() {
        return south;
    }

    public void setSouth(String south) {
        //this.south = south;
    }
}
