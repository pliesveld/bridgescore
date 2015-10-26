package pliesveld.bridge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.MultiLineLabel;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.ClientProperties;
import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;

import pliesveld.bridge.model.*;

import java.util.Arrays;

public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger();

    protected FormPanel panelForm;
    protected ScorePanel panelScore;
    protected RubberPanel panelRubber;

    private BridgeGame bridgeGame;
    private BackScore bridgeScore;

    private class ContractForm
            extends Form<FormContractModel>
    {
        private FormContractModel _formContractModel = new FormContractModel();

        private DropDownChoice<Seat> choiceSeatField;
        private DropDownChoice<Suit> choiceSuitField;
        private DropDownChoice<Penalty> choicePenaltyField;
        private RangeTextField<Integer> rangeLevelField;
        private RangeTextField<Integer> rangeTricksField;

        public ContractForm(final String id)
        {
            super(id);
            setDefaultModel(new CompoundPropertyModel<>(_formContractModel));

            setMarkupId("auctionForm");


            add((choiceSeatField = new DropDownChoice<Seat>("seat",
                    Model.of(Seat.SOUTH),
	      	    Arrays.asList(Seat.values()))));
	        choiceSeatField.setRequired(true);


            add((choiceSuitField = new DropDownChoice<Suit>("suit",
                    Model.of(Suit.CLUBS),
                    Arrays.asList(Suit.values()))));
	        choiceSuitField.setRequired(true);


            add((choicePenaltyField = new DropDownChoice<Penalty>("penalty",
                    Model.of(Penalty.UNDOUBLED),
                    Arrays.asList(Penalty.values()))));
            choicePenaltyField.setRequired(true);


            rangeLevelField = new RangeTextField<Integer>("level");
            rangeLevelField.setMinimum(1);
            rangeLevelField.setMaximum(7);
            add(rangeLevelField);

            rangeTricksField = new RangeTextField<Integer>("tricks");
            rangeTricksField.setMinimum(0);
            rangeTricksField.setMaximum(13);
            add(rangeTricksField);

        }

        @Override
        public void onSubmit()
        {

            Object objModel = getDefaultModelObject();
            if (objModel instanceof FormContractModel)
            {
                FormContractModel fcm = (FormContractModel)objModel;
                Suit t_suit = (Suit)choiceSuitField.getDefaultModelObject();
                Seat t_seat = (Seat)choiceSeatField.getDefaultModelObject();
                Penalty t_penalty = (Penalty)choicePenaltyField.getDefaultModelObject();

                fcm.setSuit(t_suit);
                fcm.setSeat(t_seat);
                fcm.setPenalty(t_penalty);

                LOG.info(fcm);

                int tricks_made = fcm.getTricks();
                AuctionContract ac = new AuctionContract(fcm.getSeat(),fcm.getSuit(),fcm.getLevel(),fcm.getPenalty());

                bridgeGame.playHand(ac, tricks_made);
            }

        }

    }

    public HomePage(final PageParameters parameters) {
		super(parameters);

        bridgeGame = ((WicketApplication)getApplication()).getGame();
        bridgeScore = bridgeGame.getBridgeScore();

        ContractForm formContract = new ContractForm("auctionForm"); 

        add((panelForm = new FormPanel("form-panel")));
        panelForm.add(formContract);

        add((panelRubber = new RubberPanel("rubber-panel")));
        add((panelScore = new ScorePanel("score-panel")));

        add(new AjaxLink("link-nextdealer") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                bridgeGame.advanceDealer();
                setResponsePage(getPage());
            }
        });

        add(new AjaxLink("link-refresh") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                getSession().invalidateNow();
                setResponsePage(getPage());
            }
        });

        add(new AjaxLink("link-resetgame") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                bridgeGame.clear();
                getSession().invalidateNow();
                setResponsePage(getPage());
            }
        });

        add(new AjaxLink("link-popgame") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                bridgeGame.pop();
                getSession().invalidateNow();
                setResponsePage(getPage());
            }
        });

        final ClientProperties properties =  ((WebClientInfo)Session.get().getClientInfo()).getProperties();
        add(new MultiLineLabel("clientinfo",properties.toString()));


        add(new FeedbackPanel("feedback"));

        setVersioned(false);

    }
}
