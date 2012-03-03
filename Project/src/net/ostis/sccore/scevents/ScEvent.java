package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScElement;

/**
 * User: yaskoam
 * Date: 03.03.12
 * Time: 13:52
 */
public class ScEvent {
    private String eventType;
    private ScElement source;

    public ScEvent(String eventType, ScElement source) {
        this.eventType = eventType;
        this.source = source;
    }
}
