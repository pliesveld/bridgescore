// vim: tabstop=4 shiftwidth=4 expandtab
package pliesveld.bridge.controller;

import pliesveld.bridge.model.*;

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
    private boolean team1vuln = false;
    private boolean team2vuln = false;

    public void playHand(AuctionContract contract, int tricks)
    {
        BridgeHand bh = new BridgeHand(contract,tricks);

        List<ScoreMarkEntry> marksList = backScore.evaluateDeclarerPlay(contract, tricks);
        bh.setMarks(marksList);
        handHistory.add(0,bh);
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
        team1vuln = false;
        team2vuln = false;
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
