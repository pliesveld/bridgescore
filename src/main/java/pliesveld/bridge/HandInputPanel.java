package pliesveld.bridge;


import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RangeTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
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
