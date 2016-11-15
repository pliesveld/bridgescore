package pliesveld.bridge.web;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pliesveld.bridge.WicketApplication;
import pliesveld.bridge.controller.BridgeGame;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Base class for all panels
 */
public class BasePanel extends Panel {
    protected static final Logger LOG = LogManager.getLogger();

    @Deprecated
    protected BridgeGame getBridgeGame()
    {
        throw new NotImplementedException();
    }

    public BasePanel(String id) {
        super(id);
    }

    public BasePanel(String id, IModel<?> model) {
        super(id, model);
    }
}
