package pliesveld.bridge.web;


import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pliesveld.bridge.controller.BridgeGame;
import pliesveld.bridge.model.*;
import pliesveld.bridge.service.PlayerService;

import java.util.Arrays;
import java.util.List;

public class HandInputPanel extends BasePanel {

    @SpringBean
    private BridgeGame bridgeGame;

    @SpringBean
    private PlayerService playerService;

    public static class PlayerRenderer extends ChoiceRenderer<Seat> {
        @Override public String getIdValue(Seat object, int index) {
            String orig = super.getIdValue(object, index);
            return orig + "!!!" + orig;
        }
    }
    private class ContractForm
            extends StatelessForm<FormContractModel>
    {
        private FormContractModel _formContractModel = new FormContractModel();

        public ContractForm(final String id)
        {
            super(id);
            /*
                CompoundPropertyModel provides a container model for looking this form's
                children components based on their registered id.  _formContractModel must
                contain getters and setters for each child member that is to be resolved.
             */
            setDefaultModel(new CompoundPropertyModel<>(_formContractModel));

            setMarkupId("auctionForm");

//            final PlayerSelection choiceSeat = new PlayerSelection("seat", new RadioChoice("seat", Arrays.asList(Seat.values())),new PlayerRenderer());
            final RadioChoice choiceSeat = new RadioChoice("seat", Arrays.asList(Seat.values()));
            choiceSeat.setRequired(true);
            choiceSeat.setMarkupId( "real_seatid" );
            add(choiceSeat);

            final RadioChoice choiceSuit = new RadioChoice("suit", Arrays.asList(Suit.values()));
            choiceSuit.setRequired(true);
            choiceSuit.setMarkupId( "real_suitid" );
            add(choiceSuit);

            final RadioChoice choicePenalty = new RadioChoice("penalty", Arrays.asList(Penalty.values()));
            choicePenalty.setRequired(true);
            choicePenalty.setMarkupId( "real_penaltyid" );
            add(choicePenalty);

            final RangeTextField<Integer> rangeLevelField = new RangeTextField<Integer>("level");
            rangeLevelField.setMinimum(1);
            rangeLevelField.setMaximum(7);
            rangeLevelField.setRequired(true);
            rangeLevelField.setMarkupId( "real_levelid" );
            add(rangeLevelField);

            final RangeTextField<Integer> rangeTricksField = new RangeTextField<Integer>("tricks");
            rangeTricksField.setMinimum(0);
            rangeTricksField.setMaximum(13);
            rangeTricksField.setRequired(true);
            rangeTricksField.setMarkupId( "real_tricksid" );
            add(rangeTricksField);
            

            add(new FeedbackPanel("feedback"));
        }

        @Override
        protected void callOnError(IFormSubmitter submitter) {
            LOG.trace("auction form callOnError()");
            super.callOnError(submitter);
        }

        @Override
        protected void onError() {
            LOG.trace("auction form onError()");
            super.onError();

            PageParameters parameters = new PageParameters();

            String fullUrl = RequestCycle.get().getUrlRenderer()
                .renderFullUrl(Url.parse((String) getPage().urlFor(HomePage.class,parameters)));
            setResponsePage(new RedirectPage(fullUrl+"#hand-page-final"));
        }


        @Override
        public void onSubmit()
        {
            LOG.trace("auction form onSubmit()");

            Object objModel = getDefaultModelObject();
            if (objModel instanceof FormContractModel)
            {
                FormContractModel fcm = (FormContractModel)objModel;

                LOG.trace("FormContractModel::OnSubmit : seat = " + fcm.getSeat());
                LOG.trace("FormContractModel::OnSubmit : suit = " + fcm.getSuit());
                LOG.trace("FormContractModel::OnSubmit : penalty = " + fcm.getPenalty());
                LOG.trace("FormContractModel::OnSubmit : level = " + fcm.getLevel());
                LOG.trace("FormContractModel::OnSubmit : tricks = " + fcm.getTricks());

                LOG.info(fcm);

                int tricks_made = fcm.getTricks();
                String playerName = playerService.getPlayerAt(fcm.getSeat());
                AuctionContract ac = new AuctionContract(fcm.getSeat(),playerName, fcm.getSuit(),fcm.getLevel(),fcm.getPenalty());
                bridgeGame.playHand(ac, tricks_made);
            }

        }
    }

    public HandInputPanel(String id) {
        super(id);
        final ContractForm form = new ContractForm("auctionForm");
        add(form);
    }
}
