package pliesveld.bridge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.MultiLineLabel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.protocol.http.ClientProperties;
import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;

import pliesveld.bridge.model.*;

public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger();

    protected HandInputPanel panelForm;
    protected ScorePanel panelScore;
    protected RubberPanel panelRubber;

    private BridgeGame bridgeGame;


    public HomePage(final PageParameters parameters) {
		super(parameters);

        bridgeGame = ((WicketApplication)getApplication()).getGame();

        add((panelForm = new HandInputPanel("hand-input-panel")));
        add((panelRubber = new RubberPanel("rubber-panel")));
        add((panelScore = new ScorePanel("score-panel")));

        add(new AjaxLink("link-nextdealer") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                LOG.info("Advanced dealer");
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
                LOG.info("Reset game score");
                bridgeGame.clear();
                getSession().invalidateNow();
                setResponsePage(getPage());
            }
        });

        add(new AjaxLink("link-popgame") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                LOG.info("Removed last score");
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
