package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScElement;

/**
 * Class for presentation event that can be happened.
 *
 * @author yaskoam
 */
public class ScEvent {
    private ScEventTypes eventType;
    private ScElement source;

    /**
     * Construct event object.
     *
     * @param eventType type of event
     * @param source element that caused event
     */
    public ScEvent(ScEventTypes eventType, ScElement source) {
        this.eventType = eventType;
        this.source = source;
    }

    /**
     * Method return sc element that connected with the event.
     *
     * @return sc element object
     */
    public ScElement getSource() {
        return source;
    }

    /**
     * Method that return type of event.
     *
     * @return type of event
     */
    public ScEventTypes getEventType() {
        return eventType;
    }
}
