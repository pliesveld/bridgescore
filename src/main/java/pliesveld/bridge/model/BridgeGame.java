// vim: tabstop=4 shiftwidth=4 expandtab
package pliesveld.bridge.model;

import org.apache.wicket.markup.html.link.Link;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.io.Serializable;

public class BridgeGame
    implements Serializable
{
	private Seat dealer = Seat.SOUTH;
	private List<BridgeHand> handHistory = new LinkedList<BridgeHand>();
    private BackScore backScore = new BackScore();
    private boolean team1vuln;
    private boolean team2vuln;

    public void playHand(AuctionContract contract, int tricks)
    {
        handHistory.add(0,new BridgeHand(contract, tricks));
        backScore.evaluateDeclarerPlay(contract, tricks);
        dealer = dealer.next();
        team1vuln = backScore.isVulnerable(Team.TEAM_NS);
        team2vuln = backScore.isVulnerable(Team.TEAM_WE);
    }

    public BackScore getBridgeScore() {
        return backScore;
    }

    public List<BridgeHand> getHandHistory() { return handHistory; }

    public void advanceDealer() {
        dealer = dealer.next();
    }

    /**
     * Resets game score, and clears hands played
     */
    public void clear() {
        dealer = Seat.SOUTH;
        handHistory.clear();
        backScore.clear();
    }

    /* removes last game from history
        and recomputes score
     */
    public void pop() {
        ArrayList<BridgeHand> history = new ArrayList<>();
        history.addAll(handHistory);
        Collections.reverse(history);

        this.clear();

        if(!history.isEmpty())
        {
            history.remove(history.size()-1);

        }
        for(BridgeHand hand : history)
        {
            AuctionContract ac = hand.getContract();
            int tricks = hand.getTricks();
            playHand(ac,tricks);
        }


    }
}
