package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;

/**
 * Class for listener, that wait attach input arc to sc node.
 *
 * @author yaskoam
 */
public class ListenerForAttachInputToNode extends ScEventListener {
    private ScActionListener listner;
    private ScElement scElement;

    /**
     * Construct listener.
     *
     * @param listner object that implement ScActionListner interface
     * @param scElement node to which add input sc arc.
     */
    public ListenerForAttachInputToNode(ScActionListener listner, ScElement scElement) {
        this.listner = listner;
        this.scElement = scElement;
    }

    /**
     * Method that get type of event for this listener.
     *
     * @return type of event
     */
    @Override
    public ScEventTypes getEventType() {
        return ScEventTypes.ATTACH_INPUT_TO_NODE;
    }

    /**
     * Method that will be executed in answer for the event.
     *
     * @param event event object, contain new arc which was attached to sc node.
     */
    @Override
    public void perform(ScEvent event) {
        listner.perform(event);
    }

    /**
     * Method that determine, if this listener suitable for happened event.
     *
     * @param event event object, contain new arc which was attached to sc node.
     * @return return true, if this listener suitable, in other case return false
     */
    @Override
    public boolean verification(ScEvent event) {
        ScElement element = event.getSource();
        if (!element.isScArc()) {
            return false;
        }

        ScArc arc = (ScArc) element;
        ScNode endNode = arc.getEndScNode();
        if (endNode == null) {
            return false;
        }

        if (endNode.getAddress() != scElement.getAddress()) {
            return false;
        }

        return true;
    }

}
