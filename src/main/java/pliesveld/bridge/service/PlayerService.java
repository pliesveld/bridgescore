package pliesveld.bridge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pliesveld.bridge.controller.BridgeGame;
import pliesveld.bridge.model.Seat;
import pliesveld.bridge.web.test.FormPlayerModel;

@Service
public class PlayerService {

    @Autowired
    private BridgeGame bridgeGame;

    public String getPlayerAt(final Seat seat) {
        String name = "";
        final String[] names = bridgeGame.getPlayerNames();
        switch (seat) {
            case SOUTH:
                name = names[0];
                break;
            case WEST:
                name = names[1];
                break;
            case NORTH:
                name = names[2];
                break;
            case EAST:
                name = names[3];
                break;
        }

        if (!StringUtils.hasLength(name)) {
            name = seat.name();
        }
        return name;
    }
}
