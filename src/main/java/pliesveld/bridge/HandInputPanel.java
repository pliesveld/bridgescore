package pliesveld.bridge;


import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RangeTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Classes;
import pliesveld.bridge.model.*;

import java.util.Arrays;

public class HandInputPanel extends BasePanel {

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
            /*
                CompoundPropertyModel provides a container model for looking this form's
                children components based on their registered id.  _formContractModel must
                contain getters and setters for each child member that is to be resolved.
             */
            setDefaultModel(new CompoundPropertyModel<>(_formContractModel));

            setMarkupId("auctionForm");

            add(choiceSeatField = new DropDownChoice<Seat>("seat",
                    Arrays.asList(Seat.values()),
                    new EnumChoiceRenderer<Seat>(this)));
            choiceSeatField.setRequired(true);

            add((choiceSuitField = new DropDownChoice<Suit>("suit",
                    Arrays.asList(Suit.values()),
                    new EnumChoiceRenderer<Suit>(this))));
            choiceSuitField.setRequired(true);

            add((choicePenaltyField = new DropDownChoice<Penalty>("penalty",
                    Arrays.asList(Penalty.values()),
                    new EnumChoiceRenderer<Penalty>(this))));
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
        ContractForm form = new ContractForm("auctionForm");
        add(form);
    }
}
