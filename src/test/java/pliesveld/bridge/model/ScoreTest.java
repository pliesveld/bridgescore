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


public class ScoreTest
{
    @Test
    public void fromBookPartScore()
    {
        BackScore score_card = new BackScore();
        AuctionContract contract = null;

        Seat us = Seat.SOUTH;
        String name = "name";
        Seat them = Seat.WEST;

        assertFalse(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));
        assertEquals(0,score_card.pointsTotal(us.team()));
        assertEquals(0,score_card.pointsTotal(them.team()));
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(0,score_card.pointsPartial(them.team()));


        contract = new AuctionContract(us,name,SPADES,2);
        score_card.evaluateDeclarerPlay(contract,8);
        assertEquals(60,score_card.pointsPartial(us.team()));
        assertEquals(0,score_card.pointsPartial(them.team()));

        contract = new AuctionContract(them,name,HEARTS,3);
        score_card.evaluateDeclarerPlay(contract,10);
        assertEquals(60,score_card.pointsPartial(us.team()));
        assertEquals(90,score_card.pointsPartial(them.team()));
        assertEquals(120,score_card.pointsTotal(them.team()));

        assertFalse(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));

        contract = new AuctionContract(us,name,NOTRUMP,1);
        score_card.evaluateDeclarerPlay(contract,7);
        assertEquals(100,score_card.pointsTotal(us.team()));
        assertEquals(120,score_card.pointsTotal(them.team()));
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(0,score_card.pointsPartial(them.team()));

        assertTrue(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));

        contract = new AuctionContract(them,name,HEARTS,1);
        score_card.evaluateDeclarerPlay(contract,7);
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(30,score_card.pointsPartial(them.team()));

        contract = new AuctionContract(them,name,SPADES,3);
        score_card.evaluateDeclarerPlay(contract,11);

        assertTrue(score_card.isVulnerable(us.team()));
        assertTrue(score_card.isVulnerable(them.team()));
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(0,score_card.pointsPartial(them.team()));

        assertEquals(100,score_card.pointsTotal(us.team()));
        assertEquals(300,score_card.pointsTotal(them.team()));

        /*
            prior score: 100
            game, bid and made in NT: 100
            2 overtricks in NT: 60
            rubber in 2: 500
         */
        contract = new AuctionContract(us,name,NOTRUMP,3);
        score_card.evaluateDeclarerPlay(contract,11);
        assertEquals(100 + 100 + 60 + 500,score_card.pointsTotal(us.team()));
        assertEquals(300,score_card.pointsTotal(them.team()));
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(0,score_card.pointsPartial(them.team()));

        /*  End of rubber */
        assertFalse(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));

	System.out.println(score_card);
    }

    @Test
    public void fromBookSlamScore()
    {
        BackScore score_card = new BackScore();
        AuctionContract contract = null;

        Seat us = Seat.SOUTH;
        String name = "name";
        Seat them = Seat.WEST;

        assertFalse(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));
        assertEquals(0,score_card.pointsTotal(us.team()));
        assertEquals(0,score_card.pointsTotal(them.team()));
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(0,score_card.pointsPartial(them.team()));

        /* took 6 tricks, but did not bid it */
        contract = new AuctionContract(us,name,HEARTS,4);
        score_card.evaluateDeclarerPlay(contract,12);
        assertEquals(180,score_card.pointsTotal(us.team()));
        assertEquals(0,score_card.pointsTotal(them.team()));
        assertTrue(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));

        /* bid and made 6: +500 for small slam */
        contract = new AuctionContract(them,name,HEARTS,6);
        score_card.evaluateDeclarerPlay(contract,12);
        assertEquals(180,score_card.pointsTotal(us.team()));
        assertEquals(6*30 + 500,score_card.pointsTotal(them.team()));
        assertTrue(score_card.isVulnerable(us.team()));
        assertTrue(score_card.isVulnerable(them.team()));


        /*
        prior: 180
        slam while vulnerable: +750
        6NT:    +190 ( 6*30 + 10 )
        rubber in two games: +500
         */
        contract = new AuctionContract(us,name,NOTRUMP,6);
        score_card.evaluateDeclarerPlay(contract,12);
        assertEquals(180 + 750 + 190 + 500,score_card.pointsTotal(us.team()));
        assertEquals(680,score_card.pointsTotal(them.team()));
        assertFalse(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));

    }

    @Test
    public void fromBookUnderscoreScore()
    {
        BackScore score_card = new BackScore();
        AuctionContract contract = null;

        Seat us = Seat.SOUTH;
        String name = "name";
        Seat them = Seat.WEST;

        assertFalse(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));
        assertEquals(0,score_card.pointsTotal(us.team()));
        assertEquals(0,score_card.pointsTotal(them.team()));
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(0,score_card.pointsPartial(them.team()));

        /* down two */
        contract = new AuctionContract(us,name,HEARTS,4);
        score_card.evaluateDeclarerPlay(contract,8);
        assertEquals(0,score_card.pointsTotal(us.team()));
        assertEquals(100, score_card.pointsTotal(them.team()));
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(0, score_card.pointsPartial(them.team()));
        assertFalse(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));

        /* we bid and make game: 4s */
        contract = new AuctionContract(us,name,SPADES,4);
        score_card.evaluateDeclarerPlay(contract,10);
        assertEquals(120,score_card.pointsTotal(us.team()));
        assertEquals(100, score_card.pointsTotal(them.team()));
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(0,score_card.pointsPartial(them.team()));
        assertTrue(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));

        /* down two vulnerable
        * prior: 100
        * down-two vuln = 100 per
        *               = 200
        *               */
        contract = new AuctionContract(us,name,HEARTS,4);
        score_card.evaluateDeclarerPlay(contract,8);
        assertEquals(120,score_card.pointsTotal(us.team()));
        assertEquals(100 + 200,score_card.pointsTotal(them.team()));
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(0, score_card.pointsPartial(them.team()));
        assertTrue(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));

	System.out.println(score_card);
    }


    @Test
    public void fromBookPenaltyScore()
    {
        BackScore score_card = new BackScore();
        AuctionContract contract = null;

        Seat us = Seat.SOUTH;
        String name = "name";
        Seat them = Seat.WEST;

        assertFalse(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));
        assertEquals(0,score_card.pointsTotal(us.team()));
        assertEquals(0,score_card.pointsTotal(them.team()));
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(0,score_card.pointsPartial(them.team()));

        /* made 1NT doubled with +1 overtrick */
        contract = new AuctionContract(us,name,NOTRUMP,1,DOUBLED);
        score_card.evaluateDeclarerPlay(contract,7);
        assertEquals(80,score_card.pointsPartial(us.team()));
        assertEquals(0,score_card.pointsPartial(them.team()));
        assertEquals(80 + 50,score_card.pointsTotal(us.team()));
        assertEquals(0, score_card.pointsTotal(them.team()));
        assertFalse(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));

        contract = new AuctionContract(them,name,SPADES,2,DOUBLED);
        score_card.evaluateDeclarerPlay(contract,8);
        assertFalse(score_card.isVulnerable(us.team()));
        assertTrue(score_card.isVulnerable(them.team())); /* we doubled them to game! */
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(0,score_card.pointsPartial(them.team()));

        assertEquals(130,score_card.pointsTotal(us.team()));
        assertEquals(120 + 50,score_card.pointsTotal(them.team()));

        /*
            them:
            prior: 170
            4Hx made: 120*2
                +50 insult
            rubber in two: +700

            overtrick:
                vulnerable doubled overtrick, each: +200
        */
        contract = new AuctionContract(them,name,HEARTS,4,DOUBLED);
        score_card.evaluateDeclarerPlay(contract,11);
        assertFalse(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(0,score_card.pointsPartial(them.team()));

        assertEquals(130,score_card.pointsTotal(us.team()));
        assertEquals(170 + 120*2 + 50 + 700 + 200,score_card.pointsTotal(them.team()));

	System.out.println(score_card);
    }

    @Test
    public void fromBookPenaltyDownScore()
    {
        BackScore score_card = new BackScore();
        AuctionContract contract = null;

        Seat us = Seat.SOUTH;
        String name = "name";
        Seat them = Seat.WEST;

        assertFalse(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));
        assertEquals(0,score_card.pointsTotal(us.team()));
        assertEquals(0,score_card.pointsTotal(them.team()));
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(0,score_card.pointsPartial(them.team()));

        /* down 3 doubled, non vuln
         *
          * -100 + 200*3
          * */
        contract = new AuctionContract(us,name,NOTRUMP,4,DOUBLED);
        score_card.evaluateDeclarerPlay(contract,7);
        assertEquals(0,score_card.pointsTotal(us.team()));
        assertEquals(500, score_card.pointsTotal(them.team()));
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(0,score_card.pointsPartial(them.team()));
        assertFalse(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));

        /*
            6c = 20*6 * 4
               = 120  * 4
               = 480

               +50 insult

               +500: non-vulnerable slam
         */
        contract = new AuctionContract(us,name,CLUBS,6,REDOUBLED);
        score_card.evaluateDeclarerPlay(contract,12);
        assertEquals(480 + 50 + 500,score_card.pointsTotal(us.team()));
        assertEquals(500, score_card.pointsTotal(them.team()));
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(0,score_card.pointsPartial(them.team()));
        assertTrue(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));


        /*
            Down two vuln redoubled, first compute doubled:

            Down two vulnerable doubled

            points = -100
            points += 300*2
                    = 500

            Then multiply by 2
            Redoubled *= 2
                       = 1000
         */
        contract = new AuctionContract(us,name,CLUBS,1,REDOUBLED);
        score_card.evaluateDeclarerPlay(contract,5);
        assertEquals(1030,score_card.pointsTotal(us.team()));
        assertEquals(1000 + 500, score_card.pointsTotal(them.team()));
        assertEquals(0,score_card.pointsPartial(us.team()));
        assertEquals(0,score_card.pointsPartial(them.team()));
        assertTrue(score_card.isVulnerable(us.team()));
        assertFalse(score_card.isVulnerable(them.team()));
	    System.out.println(score_card);
    }
}
