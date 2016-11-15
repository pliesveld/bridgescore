package pliesveld.bridge.web.test;

import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.model.AbstractWrapModel;
import org.apache.wicket.util.io.IClusterable;
import org.springframework.stereotype.Component;

import java.io.Serializable;

public class FormPlayerModel implements IClusterable, Serializable {
    String player_south = "sOUtH";
    String player_west = "wESt";
    String player_north = "nORTh";
    String player_east = "eASt";

    public String getPlayer_south() {
        return player_south;
    }

    public String getPlayer_west() {
        return player_west;
    }

    public String getPlayer_north() {
        return player_north;
    }

    public String getPlayer_east() {
        return player_east;
    }


    @Override public String toString() {
        return "FormPlayerModel{" +
                "player_south='" + player_south + '\'' +
                ", player_west='" + player_west + '\'' +
                ", player_north='" + player_north + '\'' +
                ", player_east='" + player_east + '\'' +
                '}';
    }
}

