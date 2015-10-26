package pliesveld.bridge.model;
import java.util.EnumSet;
import java.util.Arrays;

/**
 * Two teams, two players each.
 */
public enum Team
{
    /*
        AuctionContract depends on this ordering because of
         usage of ordinal()
         .
        do not add more entries.
    */
    TEAM_NS(Seat.SOUTH,Seat.NORTH)
    {
        Team other()
        {
            return TEAM_WE;
        }
    },
    TEAM_WE(Seat.WEST,Seat.EAST)
    {
        Team other()
        {
            return TEAM_NS;
        }
    };

    final private EnumSet<Seat> values = EnumSet.noneOf(Seat.class);

    Team(Seat... s)
    {
        values.addAll(Arrays.asList(s));
    }

    abstract Team other();

    public static Team of(Seat seat)
    {
	    Class<Team> teamClass = Team.class;
	    for(Team t : teamClass.getEnumConstants())
	    {
		    if(t.values.contains(seat))
			    return t;
	    }
	    return null;
    }
}
