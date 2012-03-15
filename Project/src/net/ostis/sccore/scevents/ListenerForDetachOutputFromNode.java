/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;

/**
 * Class for listener, that wait detach output arc from another arc.
 * @author yaskoam
 */
public class ListenerForDetachOutputFromNode extends ScEventListener {
    private String nodeName;
    private ScActionListener listner;

    /**
     * Construct listener.
     * @param listener object that implement ScActionListner interface
     * @param nodeName name of node from which output arc was detached.
     */
    public ListenerForDetachOutputFromNode(ScActionListener listener, String nodeName) {
        this.listner = listener;
        this.nodeName = nodeName;
    }

    /**
     * Method that get type of event for this listener.
     * @return type of event
     */
    @Override
    public String getEventType() {
        return ScEventTypes.DETACH_OUTPUT_FROM_NODE;
    }

    /**
     * Method that will be executed in answer for the event.
     * @param event event object, contain sc node from which sc arc was detach.
     */
    @Override
    public void perform(ScEvent event) {
        listner.perform(event);
    }

    /**
     * Method that determine, if this listener suitable for happened event.
     * @param event event object, contain sc node from which sc arc was detach.
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
