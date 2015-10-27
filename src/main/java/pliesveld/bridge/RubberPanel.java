package pliesveld.bridge;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.util.value.ValueMap;
import pliesveld.bridge.model.AuctionContract;
import pliesveld.bridge.model.BridgeGame;
import pliesveld.bridge.model.BridgeHand;

import java.util.List;

/**
 * Displays history of past games
 */

public class RubberPanel extends BasePanel
{


    RubberPanel(String id)
    {
        super(id);

        List<BridgeHand> list_hands = ((WicketApplication)getApplication()).getGame().getHandHistory();

        ListView<BridgeHand> bridgehandListView = new ListView<BridgeHand>("hand_list",list_hands) {
            @Override
            protected void populateItem(ListItem listItem) {
                BridgeHand hand = (BridgeHand) listItem.getModelObject();
                listItem.add(new Label("hand",hand));
            }
        };
        bridgehandListView.setOutputMarkupId(true);
                
        add(bridgehandListView);
    }


}
