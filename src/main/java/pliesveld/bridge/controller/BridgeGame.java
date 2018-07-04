package pliesveld.bridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import pliesveld.bridge.model.*;
import pliesveld.bridge.service.PlayerService;

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

    @Autowired
    private PlayerService playerService;

    public void playHand(AuctionContract contract, int tricks)
    {
        BridgeHand bridgeHand = new BridgeHand(contract,tricks);
        bridgeHand.setDeclarer(playerService.getPlayerAt(contract.getDeclarer()));
        List<ScoreMarkEntry> marksList = backScore.evaluateDeclarerPlay(contract, tricks);
        bridgeHand.setMarks(marksList);

        handHistory.add(0,bridgeHand);
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
                currentDealer = playerNames[0];
                break;
            case WEST:
                currentDealer = playerNames[1];
                break;
            case NORTH:
                currentDealer = playerNames[2];
                break;
            case EAST:
                currentDealer = playerNames[3];
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
        updateCurrentDealer();
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
        dealer = prev_dealer.next().next().next();
        updateCurrentDealer();
    }

    public Seat getDealer() {
        return dealer;
    }

    public void setDealer(Seat dealer) {
        this.dealer = dealer;
    }

    public String getCurrentDealer() { return currentDealer; }

    public String[] getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(String[] playerNames) {
//        Assert.notNull(playerNames);
//        Assert.noNullElements(playerNames);
//        Assert.notEmpty(playerNames);
//        Assert.isTrue(playerNames.length == 4);
        this.playerNames = playerNames;
        updateCurrentDealer();
    }
}
