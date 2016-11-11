package pliesveld.bridge;


import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.apache.wicket.request.Url;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import pliesveld.bridge.model.*;


/**
 *
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see pliesveld.bridge.Start#main(String[])
 */
public class WicketApplication extends WebApplication
    implements ApplicationContextAware
{
    private static final Logger LOG = LogManager.getLogger();
    private static BridgeGame singleGame = new BridgeGame();
    private ApplicationContext ctx;

    public BridgeGame getGame()
	{ return singleGame; }

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();
//        getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx, true));
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        getDebugSettings().setDevelopmentUtilitiesEnabled(true);
        getDebugSettings().setAjaxDebugModeEnabled(false);
        getJavaScriptLibrarySettings().setJQueryReference(new UrlResourceReference(Url.parse("/vendors/jquery-2.1.4.min.js")));
	}

    @Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
