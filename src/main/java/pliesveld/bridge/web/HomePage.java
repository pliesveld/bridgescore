package pliesveld.bridge.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.ClientProperties;
import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;

import org.apache.wicket.spring.injection.annot.SpringBean;
import pliesveld.bridge.WicketApplication;
import pliesveld.bridge.controller.BridgeGame;

public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger();

    private boolean stripTags;

    protected HandInputPanel panelForm;
    protected ScorePanel panelScore;
    protected RubberPanel panelRubber;

    @SpringBean
    private BridgeGame bridgeGame;


    public HomePage(final PageParameters parameters) {
		super(parameters);

        stripTags = ((WicketApplication)getApplication()).getMarkupSettings().getStripWicketTags();

        panelForm = new HandInputPanel("hand-input-panel");
        panelForm.setRenderBodyOnly(true);

        add(panelForm);
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

        add(new Label("render-count", new IModel<Object>() {
            @Override
            public Object getObject() {
                return getPage().getRenderCount();
            }

            @Override
            public void setObject(Object object) {

            }

            @Override
            public void detach() {

            }
        }
        ));


        setVersioned(false);
    }


	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		((WicketApplication)getApplication()).getMarkupSettings().setStripWicketTags(true);
	}

	@Override
	protected void onAfterRender() {
		super.onAfterRender();
		((WicketApplication)getApplication()).getMarkupSettings().setStripWicketTags(stripTags);
	}

}
