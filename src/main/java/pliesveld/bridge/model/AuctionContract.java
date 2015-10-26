package pliesveld.bridge.model;

import java.io.Serializable;

/**
 * Represents the contract, which is result of the Auction phase in bridge
 */
public class AuctionContract
    implements Serializable
{
    private Seat declarer;
    private Suit suit;
    private int level;
    private Penalty penalty;

    public AuctionContract(Seat declarer, Suit suit, int level, Penalty penalty)
    {
        this.declarer = declarer;
        this.suit = suit;
        this.level = level;
        this.penalty = penalty;
    }

    public AuctionContract(Seat declarer, Suit suit, int level)
    {
        this(declarer,suit,level,Penalty.UNDOUBLED);
    }

    public Seat getDeclarer() {
        return declarer;
    }

    public void setDeclarer(Seat declarer) {
        this.declarer = declarer;
    }

    public Suit getSuit() {
        return suit;
    }

    public Team getTeam()
    {
        return declarer.team();
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

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(32);
        sb.append("[Contract ");
        sb.append(declarer);
        sb.append(":");
        sb.append(level);
        sb.append(suit);
        switch(penalty)
        {
            case REDOUBLED:
                sb.append('x');
            case DOUBLED:
                sb.append('x');
            case UNDOUBLED:
                break;
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(other == null)
            return false;

        if(this.getClass() == other.getClass())
        {
            AuctionContract rhs = (AuctionContract) other;
            if(!declarer.equals(rhs.declarer))
                return false;

            if(level != rhs.level)
                return false;

            if(!suit.equals(rhs.suit))
                return false;

            if(!penalty.equals(rhs.penalty))
                return false;


            return true;
        }

        return false;
    }
}
