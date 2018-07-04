package pliesveld.bridge.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import pliesveld.bridge.controller.BridgeGame;

@Component
public class OnStartupEvent implements ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    private BridgeGame bridgeGame;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        String testNames[] = new String[]{"name1", "name2", "name3", "name4"};
        bridgeGame.setPlayerNames(testNames);

    }
}
