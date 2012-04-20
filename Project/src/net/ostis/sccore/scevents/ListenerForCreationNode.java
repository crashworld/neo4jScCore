package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;

/**
 * Class for listener, that wait creation sc node.
 *
 * @author yaskoam
 */
public class ListenerForCreationNode extends ScEventListener {
    private ScActionListener listner;
    private String nodeName;

    /**
     * Construct listener.
     *
     * @param listner object that implement ScActionListner interface
     * @param nodeName node name which was created.
     */
    public ListenerForCreationNode(ScActionListener listner, String nodeName) {
        this.listner = listner;
        this.nodeName = nodeName;
    }

    /**
     * Method that get type of event for this listener.
     *
     * @return type of event
     */
    @Override
    public ScEventTypes getEventType() {
        return ScEventTypes.CREATE_SC_NODE;
    }


    /**
     * Method that will be executed in answer for the event.
     *
     * @param event event object, contain created sc node.
     */
    @Override
    public void perform(ScEvent event) {
        listner.perform(event);
    }

    /**
     * Method that determine, if this listener suitable for happened event.
     *
     * @param event event object, contain created sc node.
     * @return return true, if this listener suitable, in other case return false
     */
    @Override
    public boolean verification(ScEvent event) {
        ScElement element = event.getSource();
        if (!element.isScNode()) {
            return false;
        }

        ScNode node = (ScNode) element;
        if (!nodeName.equals(node.getName())) {
            return false;
        }
        return true;
    }
}
