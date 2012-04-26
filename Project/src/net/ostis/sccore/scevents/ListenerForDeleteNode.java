package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;

/**
 * Class for listener, that wait removing sc node.
 *
 * @author yaskoam
 */
public class ListenerForDeleteNode extends ScEventListener {
    private ScActionListener listener;
    private ScElement scElement;


    /**
     * Construct listener.
     *
     * @param listener object that implement ScActionListener interface
     * @param scElement node which will be deleted.
     */
    public ListenerForDeleteNode(ScActionListener listener, ScElement scElement) {
        this.scElement = scElement;
        this.listener = listener;
    }

    /**
     * Method that get type of event for this listener.
     *
     * @return type of event
     */
    @Override
    public ScEventTypes getEventType() {
        return ScEventTypes.DELETE_SC_NODE;
    }

    /**
     * Method that will be executed in answer for the event.
     *
     * @param event event object, contain node that will be deleted.
     */
    @Override
    public void perform(ScEvent event) {
        listener.perform(event);
    }

    /**
     * Method that determine, if this listener suitable for happened event.
     *
     * @param event event object, contain node which will be deleted.
     * @return return true, if this listener suitable, in other case return false
     */
    @Override
    public boolean verification(ScEvent event) {
        ScElement element = event.getSource();
        if (!element.isScNode()) {
            return false;
        }

        ScNode node = (ScNode) element;
        return node.getAddress() == scElement.getAddress();

    }

}
