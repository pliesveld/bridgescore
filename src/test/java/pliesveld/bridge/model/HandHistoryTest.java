package pliesveld.bridge.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import static pliesveld.bridge.model.Suit.CLUBS;
import static pliesveld.bridge.model.Suit.DIAMONDS;
import static pliesveld.bridge.model.Suit.HEARTS;
import static pliesveld.bridge.model.Suit.SPADES;
import static pliesveld.bridge.model.Suit.NOTRUMP;

import static pliesveld.bridge.model.Penalty.UNDOUBLED;
import static pliesveld.bridge.model.Penalty.DOUBLED;
import static pliesveld.bridge.model.Penalty.REDOUBLED;

import org.junit.Test;
import org.junit.Before;

public class HandHistoryTest
{
    @Test
    public void sampleHistory()
    {
	    BridgeGame game = new BridgeGame();
	    AuctionContract ac = new AuctionContract(Seat.SOUTH,SPADES,4);
	    int tricks = 10;

	    game.playHand(ac,tricks);

    }
}
 
