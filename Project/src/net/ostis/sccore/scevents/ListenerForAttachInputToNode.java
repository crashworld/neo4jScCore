/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;

/**
 * Class for listener, that wait attach input arc to sc node.
 * @author yaskoam
 */
public class ListenerForAttachInputToNode extends ScEventListener {
    private ScActionListener listner;
    private String nodeName;

    /**
     * Construct listener.
     * @param listner object that implement ScActionListner interface
     * @param nodeName node name to which add input sc arc.
     */
    public ListenerForAttachInputToNode(ScActionListener listner, String nodeName) {
        this.listner = listner;
        this.nodeName = nodeName;
    }

    /**
     * Method that get type of event for this listener.
     * @return type of event
     */
    @Override
    public String getEventType() {
        return ScEventTypes.ATTACH_INPUT_TO_NODE;
    }

    /**
     * Method that will be executed in answer for the event.
     * @param event event object, contain new arc which was attached to sc node.
     */
    @Override
    public void perform(ScEvent event) {
        listner.perform(event);
    }

    /**
     * Method that determine, if this listener suitable for happened event.
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

        if (!endNode.getName().equals(nodeName)) {
            return false;
        }

        return true;
    }

}
