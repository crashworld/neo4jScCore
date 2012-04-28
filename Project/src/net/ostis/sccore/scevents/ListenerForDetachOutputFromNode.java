package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScElement;

/**
 * Class for listener, that wait detach output arc from another arc.
 *
 * @author yaskoam
 */
public class ListenerForDetachOutputFromNode extends ScEventListener {
    private ScActionListener listener;
    private ScElement scElement;

    /**
     * Construct listener.
     *
     * @param listener object that implement ScActionListener interface
     * @param scElement node from which output arc was detached.
     */
    public ListenerForDetachOutputFromNode(ScActionListener listener, ScElement scElement) {
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
        return ScEventTypes.DETACH_OUTPUT_FROM_NODE;
    }

    /**
     * Method that will be executed in answer for the event.
     *
     * @param event event object, contain sc node from which sc arc was detach.
     */
    @Override
    public void perform(ScEvent event) {
        listener.perform(event);
    }

    /**
     * Method that determine, if this listener suitable for happened event.
     *
     * @param event event object, contain sc node from which sc arc was detach.
     * @return return true, if this listener suitable, in other case return false
     */
    @Override
    public boolean verification(ScEvent event) {
        ScElement element = event.getSource();
        return element.isScNode() && (element.getAddress() == scElement.getAddress());

    }
}
