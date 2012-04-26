package net.ostis.sccore.scevents;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Class that provide event mechanism.
 *
 * @author yaskoam
 */
public class ScEventHandler {
    private static ScEventHandler eventHandler;
    private Map<ScEventTypes, List<ScEventListener>> subscribersMap;

    private ScEventHandler() {


        subscribersMap = new EnumMap<ScEventTypes, List<ScEventListener>>(ScEventTypes.class);

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
     *
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
     *
     * @param eventListener subscriber object
     */
    public void subscribeOnEvent(ScEventListener eventListener) {
        List<ScEventListener> subscribersList = subscribersMap.get(eventListener.getEventType());
        subscribersList.add(eventListener);
    }

    /**
     * Method that notify all suitable subscriber about happened event.
     *
     * @param event event object
     */
    public void notify(ScEvent event) {
        List<ScEventListener> subscribersList = subscribersMap.get(event.getEventType());

        for (ScEventListener currentListener : subscribersList) {
            boolean isChecked = currentListener.verification(event);
            if (isChecked) {
                currentListener.perform(event);
            }
        }
    }
}
