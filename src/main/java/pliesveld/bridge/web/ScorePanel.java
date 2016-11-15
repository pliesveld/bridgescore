package pliesveld.bridge.web;


import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.model.*;

import org.apache.wicket.markup.html.basic.Label;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pliesveld.bridge.model.BackScore;
import pliesveld.bridge.controller.BridgeGame;
import pliesveld.bridge.model.Seat;
import pliesveld.bridge.service.PlayerService;
import pliesveld.bridge.web.test.FormPlayerModel;

import java.io.Serializable;

public class ScorePanel extends BasePanel
{
    @SpringBean
    private PlayerService playerService;

    @SpringBean
    private BridgeGame bridgeGame;

    public ScorePanel(String id)
    {
        super(id);
        BackScore bridgeScore = bridgeGame.getBridgeScore();

        add(new Label("player_south", new PropertyModel<>(bridgeGame, "playerNames.0")));
        add(new Label("player_west", new PropertyModel<>(bridgeGame, "playerNames.1")));
        add(new Label("player_north", new PropertyModel<>(bridgeGame, "playerNames.2")));
        add(new Label("player_east", new PropertyModel<>(bridgeGame, "playerNames.3")));


        Label score1_vulnerbale = new VulnerabilityLabel("score_team1_vulnerable",new PropertyModel<>(bridgeGame,"team1vuln"));
        Label score2_vulnerbale = new VulnerabilityLabel("score_team2_vulnerable",new PropertyModel<>(bridgeGame,"team2vuln"));


        /*
                Use attribute modifier to add modify class tag
                for displaying vulnerability status
                score1_vulnerbale.add(new SimpleAttributeModifier)
         */

        add(score1_vulnerbale);
        add(score2_vulnerbale);

        IModel<Integer> model_score1 = new PropertyModel<>(bridgeScore,"points_above_line.0");
        IModel<Integer> model_score2 = new PropertyModel<>(bridgeScore,"points_above_line.1");

        add(new Label("score_team1",model_score1));
    	add(new Label("score_team2",model_score2));

        IModel<Integer> model_score1_partial = new PropertyModel<>(bridgeScore,"current_game.points_below_line.0");
        IModel<Integer> model_score2_partial = new PropertyModel<>(bridgeScore,"current_game.points_below_line.1");

        add(new Label("score_team1_partial",model_score1_partial));
        add(new Label("score_team2_partial",model_score2_partial));

        IModel<String> dealer = new PropertyModel<>(bridgeGame, "currentDealer");
        add(new Label("dealer", dealer)).setOutputMarkupId(true);

        setOutputMarkupId(true);
    }

    /*
        label sets the visibility based on the model
        it contains through the onConfigure overload.

        adds to class tag "vulnerable" when underlying model is true.
        modifies markup to return string "vulnerable" when true
     */
    class VulnerabilityLabel extends Label
    {
        public VulnerabilityLabel(String id, IModel<?> model) {
            super(id, model);
        }

        public VulnerabilityLabel(String id, Serializable label) {
            super(id, label);
        }

        @Override
        public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
            boolean obj = (boolean) getDefaultModelObject();
            if(obj)
            {
                replaceComponentTagBody(markupStream,openTag,"Vulnerable");
            } else {
                replaceComponentTagBody(markupStream,openTag,"Not Vulnerable");
            }
        }

        @Override
        protected void onConfigure() {
            super.onConfigure();

            String obj = getDefaultModelObjectAsString();

            boolean isVuln = Boolean.valueOf(obj);
            this.setVisible(isVuln);
        };

        @Override
        protected void onComponentTag(ComponentTag tag) {
            super.onComponentTag(tag);
            boolean obj = (boolean) getDefaultModelObject();
            if(obj)
            {
                tag.put("class","vulnerable");
            } else {
                tag.put("class","not-vulnerable");
            }
        }


    }
}
