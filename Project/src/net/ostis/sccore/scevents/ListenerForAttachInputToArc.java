package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScElement;

/**
 * Class for listener, that wait attach input arc to another arc.
 *
 * @author yaskoam
 */
public class ListenerForAttachInputToArc extends ScEventListener {
    private ScActionListener listener;
    private ScElement scElement;

    /**
     * Construct listener.
     *
     * @param listener object that implement ScActionListener interface
     * @param scElement to which the arc is held
     */
    public ListenerForAttachInputToArc(ScActionListener listener, ScElement scElement) {
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
        return ScEventTypes.ATTACH_INPUT_TO_ARC;
    }

    /**
     * Method that will be executed in answer for the event.
     *
     * @param event event object, contain new arc which was attached to other arc.
     */
    @Override
    public void perform(ScEvent event) {
        listener.perform(event);
    }

    /**
     * Method that determine, if this listener suitable for happened event.
     *
     * @param event event object, contain new arc which was attached to other arc.
     * @return return true, if this listener suitable, in other case return false
     */
    @Override
    public boolean verification(ScEvent event) {
        ScElement element = event.getSource();
        if (!element.isScArc()) {
            return false;
        }

        ScArc arc = (ScArc) element;
        ScArc endArc = arc.getEndScArc();
        return endArc != null && (endArc.getAddress() == this.scElement.getAddress());

    }
}
