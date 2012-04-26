package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScElement;

/**
 * Class for listener, that wait detach input arc from another arc.
 *
 * @author yaskoam
 */
public class ListenerForDetachInputFromArc extends ScEventListener {
    private ScActionListener listener;
    private ScElement scElement;

    /**
     * Construct listener.
     *
     * @param listener object that implement ScActionListener interface
     * @param scElement arc from which another arc was detached.
     */
    public ListenerForDetachInputFromArc(ScActionListener listener, ScElement scElement) {
        this.listener = listener;
        this.scElement = scElement;
    }

    /**
     * Method that get type of event for this listener.
     *
     * @return type of event
     */
    @Override
    public ScEventTypes getEventType() {
        return ScEventTypes.DETACH_INPUT_FROM_ARC;
    }

    /**
     * Method that will be executed in answer for the event.
     *
     * @param event event object, contain sc arc from which other arc was detach.
     */
    @Override
    public void perform(ScEvent event) {
        listener.perform(event);
    }

    /**
     * Method that determine, if this listener suitable for happened event.
     *
     * @param event event object, contain sc arc from which other arc was detach.
     * @return return true, if this listener suitable, in other case return false
     */
    @Override
    public boolean verification(ScEvent event) {
        ScElement element = event.getSource();
        if (!element.isScArc()) {
            return false;
        }

        if (element.getAddress() != scElement.getAddress()) {
            return true;
        }

        return true;
    }

}
