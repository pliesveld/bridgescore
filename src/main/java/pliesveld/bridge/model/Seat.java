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
    WEST {
        Team team()
        { return Team.TEAM_WE; }

        Seat next()
        { return Seat.NORTH; }
    },
    NORTH {
        Team team()
        { return Team.TEAM_NS; }

        Seat next()
        { return Seat.EAST; }
    },
    EAST {
        Team team()
        { return Team.TEAM_WE; }

        Seat next()
        { return Seat.SOUTH; }
    };

    abstract Team team();
    abstract Seat next();
}
