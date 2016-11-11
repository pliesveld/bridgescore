package pliesveld.bridge.web;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import pliesveld.bridge.WicketApplication;
import pliesveld.bridge.model.*;

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
                List<ScoreMarkEntry> marks = hand.getMarks();

                WebMarkupContainer wmc = new WebMarkupContainer("container-score-mark");

                wmc.add(new Label("hand",hand));

                Team team = null;

                StringBuilder sb = new StringBuilder();
                for(ScoreMarkEntry m : marks)
                {
                    ScoreMark mark = m.getMark();
                    int points = m.getPoints();
                    team = m.getTeam();

                    sb.append(mark + " " + points + "\n");
                }

                final Team finalTeam = team; // referencing a final from inside an anonymous?
                MultiLineLabel score_label = new MultiLineLabel("score-breakdown",sb.toString())
                {
                    @Override
                    protected void onComponentTag(ComponentTag tag) {
                        super.onComponentTag(tag);
                        String old_class = tag.getAttribute("class");
                        if(finalTeam == Team.TEAM_NS)
                        {
                            tag.put("class","team1 " + old_class);
                        } else if(finalTeam == Team.TEAM_WE)
                        {
                            tag.put("class","team2 " + old_class);
                        }
                    }
                };



                wmc.add(score_label);
                listItem.add(wmc);
            }
        };
        bridgehandListView.setOutputMarkupId(true);
                
        add(bridgehandListView);
    }


}
