/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScNode;

/**
 * Class for listener, that wait attach output arc to node.
 * @author yaskoam
 */
public class ListenerForAttachOutputToNode extends ScEventListener {
    private String nodeName;
    private ScActionListener listner;

    /**
     * Construct listener.
     * @param listner object that implement ScActionListner interface
     * @param nodeName node name to which add output sc arc.
     */
    public ListenerForAttachOutputToNode(ScActionListener listner, String nodeName) {
        this.listner = listner;
        this.nodeName = nodeName;
    }

    /**
     * Method that get type of event for this listener.
     * @return type of event
     */
    @Override
    public String getEventType() {
        return ScEventTypes.ATTACH_OUTPUT_TO_NODE;
    }

    /**
     * Method that will be executed in answer for the event.
     * @param event event object, contain new arc which was attached to other sc node.
     */
    @Override
    public void perform(ScEvent event) {
        listner.perform(event);
    }

    /**
     * Method that determine, if this listener suitable for happened event.
     * @param event event object, contain new arc which was attached to other sc node.
     * @return return true, if this listener suitable, in other case return false
     */
    @Override
    public boolean verification(ScEvent event) {
        ScElement element = event.getSource();
        if (!element.isScArc()) {
            return false;
        }

        ScArc arc = (ScArc) element;
        ScNode startNode = arc.getStartScNode();
        if (startNode == null) {
            return false;
        }

        if (!startNode.getName().equals(nodeName)) {
            return false;
        }

        return true;
    }


}
