package net.ostis.sccore.scevents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that provide event mechanism.
 *
 * User: yaskoam
 * Date: 03.03.12
 * Time: 13:52
 */
public class ScEventHandler {
    private static ScEventHandler eventHandler;
    private Map<String, List<ScEventListener> > subscribersMap;

    private ScEventHandler() {
        subscribersMap = new HashMap<String, List<ScEventListener> >();
        List<ScEventListener> subscribersList = new ArrayList<ScEventListener>();
        subscribersMap.put(ScEventTypes.ATTACH_INPUT_TO_ARC, subscribersList);

        subscribersList = new ArrayList<ScEventListener>();
        subscribersMap.put(ScEventTypes.ATTACH_INPUT_TO_NODE, subscribersList);

        subscribersList = new ArrayList<ScEventListener>();
        subscribersMap.put(ScEventTypes.ATTACH_OUTPUT_TO_NODE, subscribersList);

        subscribersList = new ArrayList<ScEventListener>();
        subscribersMap.put(ScEventTypes.DETACH_INPUT_FROM_NODE, subscribersList);

        subscribersList = new ArrayList<ScEventListener>();
        subscribersMap.put(ScEventTypes.DETACH_OUTPUT_FROM_NODE, subscribersList);

        subscribersList = new ArrayList<ScEventListener>();
        subscribersMap.put(ScEventTypes.DETACH_INPUT_FROM_ARC, subscribersList);

        subscribersList = new ArrayList<ScEventListener>();
        subscribersMap.put(ScEventTypes.CREATE_SC_NODE, subscribersList);

        subscribersList = new ArrayList<ScEventListener>();
        subscribersMap.put(ScEventTypes.DELETE_SC_NODE, subscribersList);
    }

    /**
     * Method that get event handler object.
     * @return event handler object
     */
    public static ScEventHandler getInstance() {
        if (eventHandler == null) {
            eventHandler = new ScEventHandler();
        }

        return eventHandler;
    }

    /**
     * Method that provide subscription on some type of event.
     * @param eventType type of event
     * @param listner subscriber object
     */
    public void subscribeOnEvent(ScEventListener eventListner) {
        List<ScEventListener> subscribersList = subscribersMap.get(eventListner.getEventType());
        subscribersList.add(eventListner);
    }

    /**
     * Method that notify all suitable subscriber about happened event.
     * @param event event object
     */
    public void notify(ScEvent event) {
        List<ScEventListener> subscribersList = subscribersMap.get(event.getEventType());
        for (ScEventListener currentListner : subscribersList) {
            boolean isChecked = currentListner.verification(event);
            if (isChecked) {
                currentListner.perform(event);
            }
        }
    }
}
