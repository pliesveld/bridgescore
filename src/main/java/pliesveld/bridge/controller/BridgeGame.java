// vim: tabstop=4 shiftwidth=4 expandtab
package pliesveld.bridge.controller;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import pliesveld.bridge.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.io.Serializable;

@Component
public class BridgeGame
    implements Serializable
{
    private String[] playerNames = new String[]{"South", "West", "North", "East"};
    private String currentDealer = "SOUth";
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
        updateCurrentDealer();
        team1vuln = backScore.isVulnerable(Team.TEAM_NS);
        team2vuln = backScore.isVulnerable(Team.TEAM_WE);
    }

    public BackScore getBridgeScore() {
        return backScore;
    }

    public List<BridgeHand> getHandHistory() { return handHistory; }

    public void advanceDealer() {
        dealer = dealer.next();
        updateCurrentDealer();
    }

    private void updateCurrentDealer() {
        switch (dealer) {
            case SOUTH:
                currentDealer = new String(playerNames[0]);
                break;
            case WEST:
                currentDealer = new String(playerNames[1]);
                break;
            case NORTH:
                currentDealer = new String(playerNames[2]);
                break;
            case EAST:
                currentDealer = new String(playerNames[3]);
                break;
        }
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
        Seat prev_dealer = dealer;
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

    public Seat getDealer() {
        return dealer;
    }

    public void setDealer(Seat dealer) {
        this.dealer = dealer;
    }

    public String[] getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(String[] playerNames) {
        Assert.notNull(playerNames);
        Assert.noNullElements(playerNames);
        Assert.notEmpty(playerNames);
        Assert.isTrue(playerNames.length == 4);
        this.playerNames = playerNames;
    }
}
