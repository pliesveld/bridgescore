package pliesveld.bridge.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import static pliesveld.bridge.model.Suit.SPADES;

import org.junit.Test;
import pliesveld.bridge.controller.BridgeGame;

public class HandHistoryTest
{
    @Test
    public void sampleHistory()
    {
	    BridgeGame game = new BridgeGame();
	    AuctionContract ac = new AuctionContract(Seat.SOUTH,"name", SPADES,4);
	    int tricks = 10;
	    game.playHand(ac,tricks);
    }
}
 
