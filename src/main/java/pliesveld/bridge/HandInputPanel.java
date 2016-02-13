package pliesveld.bridge;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.markup.html.form.RangeTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.lang.Classes;
import pliesveld.bridge.model.*;

import java.util.Arrays;

public class HandInputPanel extends BasePanel {

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
            // TODO Auto-generated method stub
            super.callOnError(submitter);
        }

        @Override
        protected void onError() {
            LOG.trace("auction form onError()");
            // TODO Auto-generated method stub
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
                AuctionContract ac = new AuctionContract(fcm.getSeat(),fcm.getSuit(),fcm.getLevel(),fcm.getPenalty());

                BridgeGame bridgeGame = getBridgeGame();
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
