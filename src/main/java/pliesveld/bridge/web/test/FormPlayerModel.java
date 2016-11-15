package pliesveld.bridge.web.test;

import org.apache.wicket.util.io.IClusterable;

/**
* Created by happs on 11/11/16.
*/
public final class FormPlayerModel implements IClusterable {
    String player_south;
    String player_west;
    String player_north;
    String player_east;

    @Override public String toString() {
        return "FormPlayerModel{" +
                "player_south='" + player_south + '\'' +
                ", player_west='" + player_west + '\'' +
                ", player_north='" + player_north + '\'' +
                ", player_east='" + player_east + '\'' +
                '}';
    }
}
