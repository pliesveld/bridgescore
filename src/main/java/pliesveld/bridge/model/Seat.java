package pliesveld.bridge.model;

/**
 * Four players to a game
 */
public enum Seat
{
    SOUTH {
        Team team()
        { return Team.TEAM_NS; }
	Seat next()
	{ return Seat.WEST; }
    },
    EAST {
        Team team()
        { return Team.TEAM_WE; }
	Seat next()
	{ return Seat.SOUTH; }
    },
    NORTH {
        Team team()
        { return Team.TEAM_NS; }
	Seat next()
	{ return Seat.EAST; }
    },
    WEST {
        Team team()
        { return Team.TEAM_WE; }
	Seat next()
	{ return Seat.NORTH; }
    };

    abstract Team team();
    abstract Seat next();


}
