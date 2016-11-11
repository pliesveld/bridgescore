package pliesveld.bridge.web;

import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.model.IModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pliesveld.bridge.WicketApplication;
import pliesveld.bridge.model.BridgeGame;

/**
 * Base class for all panels
 */
public class BaseBorder extends Border {
    protected static final Logger LOG = LogManager.getLogger();

    protected BridgeGame getBridgeGame()
    {
        return ((WicketApplication)getApplication()).getGame();
    }

    public BaseBorder(String id) {
        super(id);
    }

    public BaseBorder(String id, IModel<?> model) {
        super(id, model);
    }
}
