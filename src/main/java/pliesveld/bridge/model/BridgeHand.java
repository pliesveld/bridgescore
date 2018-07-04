// vim: tabstop=4 shiftwidth=4 expandtab
package pliesveld.bridge.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import pliesveld.bridge.service.PlayerService;

import java.io.Serializable;
import java.util.List;

public class BridgeHand
    implements Serializable
{
    private AuctionContract contract;
    private int tricks;
    private List<ScoreMarkEntry> marks;
    private String declarer;

    public BridgeHand(AuctionContract contract, int tricks)
    {
        this.setContract(contract);
        this.setTricks(tricks);
    }

    public AuctionContract getContract() {
        return contract;
    }

    public void setContract(AuctionContract contract) {
        this.contract = contract;
    }

    public int getTricks() {
        return tricks;
    }

    public void setTricks(int tricks) {
        this.tricks = tricks;
    }

    public List<ScoreMarkEntry> getMarks() {
        return marks;
    }

    public void setMarks(List<ScoreMarkEntry> marks) {
        this.marks = marks;
    }

    public String getDeclarer() { return declarer; }

    public void setDeclarer(String declarer) { this.declarer = declarer; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(45);
        AuctionContract ac = getContract();

        int expected = 6 + ac.getLevel();
        int cmp = getTricks() - expected;

        if (cmp < 0) {
            sb.append("SET  ");
        } else {
            sb.append("MADE ");
        }

        if (StringUtils.hasLength(declarer)) {
            sb.append(declarer);
        } else {
            sb.append(ac.getDeclarer());
        }

        sb.append(' ');
        sb.append(ac.getLevel());
        sb.append(ac.getSuit());
        Penalty p = ac.getPenalty();

        if(p == Penalty.DOUBLED)
            sb.append("x");
        if(p == Penalty.REDOUBLED)
            sb.append("xx");

        sb.append(' ');

        if(cmp < 0)
        {
            int down = Math.abs(cmp);
            sb.append(" DOWN " + down);
        }
        else
        {
            if(cmp > 0)
            {
                sb.append("+" + cmp);
            }
        }

        return sb.toString();

    }

}


