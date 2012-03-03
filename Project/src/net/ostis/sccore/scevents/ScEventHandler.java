package net.ostis.sccore.scevents;

/**
 * User: yaskoam
 * Date: 03.03.12
 * Time: 13:52
 */
public class ScEventHandler {
    private static ScEventHandler eventHandler;

    private ScEventHandler() {

    }

    /**
     * Method that notify all subscriber about happened event.
     * @param event event object
     */
    public void notify(ScEvent event) {

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
}
