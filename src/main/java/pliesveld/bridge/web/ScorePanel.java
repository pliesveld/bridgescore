package pliesveld.bridge.web;


import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.model.IModel;

import org.apache.wicket.markup.html.basic.Label;

import org.apache.wicket.model.PropertyModel;
import pliesveld.bridge.WicketApplication;
import pliesveld.bridge.model.BackScore;
import pliesveld.bridge.model.BridgeGame;

import java.io.Serializable;


/**
 * Displays history of past games
 */

public class ScorePanel extends BasePanel
{

    ScorePanel(String id)
    {
        super(id);

        BridgeGame bridgeGame = ((WicketApplication)getApplication()).getGame();
        BackScore bridgeScore = bridgeGame.getBridgeScore();

        Label score1_vulnerbale = new LabelVulnerable("score_team1_vulnerable",new PropertyModel<>(bridgeGame,"team1vuln"));
        Label score2_vulnerbale = new LabelVulnerable("score_team2_vulnerable",new PropertyModel<>(bridgeGame,"team2vuln"));

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

        add(new Label("dealer",new PropertyModel<>(bridgeGame,"dealer")));

        setOutputMarkupId(true);
    }

    /*
        label sets the visibility based on the model
        it contains through the onConfigure overload.

        adds to class tag "vulnerable" when underlying model is true.
        modifies markup to return string "vulnerable" when true
     */
    class LabelVulnerable extends Label
    {
        public LabelVulnerable(String id, IModel<?> model) {
            super(id, model);
        }

        public LabelVulnerable(String id, Serializable label) {
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
