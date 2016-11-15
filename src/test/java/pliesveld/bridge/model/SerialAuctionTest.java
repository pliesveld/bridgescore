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

import pliesveld.bridge.model.AuctionContract;
import org.junit.Test;
import org.junit.Before;

import java.io.*;

/**
 * Created by happs on 10/21/15.
 */
public class SerialAuctionTest {

    @Test
    public void serializeAuction() throws IOException, ClassNotFoundException {
        Seat a = Seat.EAST;
        Suit s = Suit.SPADES;
        int l = 6;
        String n = "n";
        Penalty p = Penalty.REDOUBLED;

        AuctionContract ac = new AuctionContract(a,n,s,l,p);

        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("auction.out"));

        out.writeObject("Auction Storage\n");
        out.writeObject(ac);
        out.close();

        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("auction.out"));

        String str = (String)in.readObject();
        AuctionContract ac2 = (AuctionContract)in.readObject();

        assertEquals("Auction Storage\n",str);

        assertTrue("Auctions were not equal",ac.equals(ac2));
        assertEquals(ac,ac2);


    }

}
