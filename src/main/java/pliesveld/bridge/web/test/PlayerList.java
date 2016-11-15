package pliesveld.bridge.web.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Component;
import pliesveld.bridge.controller.BridgeGame;
import pliesveld.bridge.service.PlayerService;

import java.util.Arrays;

public class PlayerList extends WebPage {
    private static final Logger LOG = LogManager.getLogger();
    private static final String[] REGULAR_PLAYERS = new String[]{ "Patrick", "Anthony", "Constantine", "Oleta" };

    @SpringBean
    private PlayerService playerService;

    @SpringBean
    private BridgeGame bridgeGame;

    private FormPlayerModel formPlayerModel = new FormPlayerModel();

    public class PlayerSeatForm extends StatelessForm<FormPlayerModel> {
        public PlayerSeatForm(String id) {
            super(id);
            setDefaultModel(new CompoundPropertyModel<FormPlayerModel>(formPlayerModel));
            setMarkupId("playerSeatForm");

            final RadioChoice choiceSouth = new RadioChoice("player_south", Arrays.asList(REGULAR_PLAYERS));
            choiceSouth.setRequired(true);
//            choiceSouth.setMarkupId("player_south");
            add(choiceSouth);

            final RadioChoice choiceWest = new RadioChoice("player_west", Arrays.asList(REGULAR_PLAYERS));
            choiceWest.setRequired(true);
//            choiceWest.setMarkupId("player_west");
            add(choiceWest);

            final RadioChoice choiceNorth = new RadioChoice("player_north", Arrays.asList(REGULAR_PLAYERS));
            choiceNorth.setRequired(true);
//            choiceNorth.setMarkupId("player_north");
            add(choiceNorth);

            final RadioChoice choiceEast = new RadioChoice("player_east", Arrays.asList(REGULAR_PLAYERS));
            choiceEast.setRequired(true);
//            choiceEast.setMarkupId("player_east");
            add(choiceEast);
        }

        @Override protected void onSubmit() {
            LOG.info(formPlayerModel);
            String[] names = new String[] {
                formPlayerModel.player_south,
                formPlayerModel.player_west,
                formPlayerModel.player_north,
                formPlayerModel.player_east
            };

            bridgeGame.setPlayerNames(names);
            super.onSubmit();
        }

        @Override protected void onError() {
            LOG.info(formPlayerModel);
            super.onError();
        }

        @Override protected void onValidate() {
            super.onValidate();
        }
    }


    public PlayerList(PageParameters parameters) {
        super(parameters);
        final PlayerSeatForm form = new PlayerSeatForm("playerSeatForm");
        add(form);
        add(new FeedbackPanel("feedback"));
    }
}
