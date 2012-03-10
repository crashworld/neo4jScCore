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
    private Map<String, List<ScActionListner> > subscribersMap;

    private ScEventHandler() {
        subscribersMap = new HashMap<String, List<ScActionListner> >();
        List<ScActionListner> subscribersList = new ArrayList<ScActionListner>();
        subscribersMap.put(ScEventTypes.ATTACH_INPUT_TO_ARC, subscribersList);

        subscribersList = new ArrayList<ScActionListner>();
        subscribersMap.put(ScEventTypes.ATTACH_INPUT_TO_NODE, subscribersList);

        subscribersList = new ArrayList<ScActionListner>();
        subscribersMap.put(ScEventTypes.ATTACH_OUTPUT_TO_NODE, subscribersList);

        subscribersList = new ArrayList<ScActionListner>();
        subscribersMap.put(ScEventTypes.CREATE_SC_ARC, subscribersList);

        subscribersList = new ArrayList<ScActionListner>();
        subscribersMap.put(ScEventTypes.CREATE_SC_NODE, subscribersList);

        subscribersList = new ArrayList<ScActionListner>();
        subscribersMap.put(ScEventTypes.DELETE_SC_ARC, subscribersList);

        subscribersList = new ArrayList<ScActionListner>();
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
    public void subscribeOnEvent(String eventType, ScActionListner listner) {
        List<ScActionListner> subscribersList = subscribersMap.get(eventType);
        subscribersList.add(listner);
    }

    /**
     * Method that notify all suitable subscriber about happened event.
     * @param event event object
     */
    public void notify(ScEvent event) {
        List<ScActionListner> subscribersList = subscribersMap.get(event.getEventType());
        for (ScActionListner currentListner : subscribersList) {
            boolean isChecked = currentListner.verification(event);
            if (isChecked) {
                currentListner.perform(event);
            }
        }
    }
}
