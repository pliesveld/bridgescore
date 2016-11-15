package pliesveld.bridge.model;

/**
 * Four players to a game
 */
public enum Seat
{
    SOUTH {
        public Team team()
        { return Team.TEAM_NS; }

        public Seat next()
        { return Seat.WEST; }
    },
    WEST {
        public Team team()
        { return Team.TEAM_WE; }

        public Seat next()
        { return Seat.NORTH; }
    },
    NORTH {
        public Team team()
        { return Team.TEAM_NS; }

        public Seat next()
        { return Seat.EAST; }
    },
    EAST {
        public Team team()
        { return Team.TEAM_WE; }

        public Seat next()
        { return Seat.SOUTH; }
    };

    public abstract Team team();
    public abstract Seat next();
}
