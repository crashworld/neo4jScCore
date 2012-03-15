package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScElement;

/**
 * Class for presentation event that can be happened.
 * @author yaskoam
 */
public class ScEvent {
    private String eventType;
    private ScElement source;

    /**
     * Construct event object.
     * @param eventType type of event
     * @param source element that caused event
     */
    public ScEvent(String eventType, ScElement source) {
        this.eventType = eventType;
        this.source = source;
    }

    public ScElement getSource() {
        return source;
    }

    public String getEventType() {
        return eventType;
    }
}
