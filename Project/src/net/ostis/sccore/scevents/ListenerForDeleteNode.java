/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;

/**
 * Class for listener, that wait removing sc node.
 * @author yaskoam
 */
public class ListenerForDeleteNode extends ScEventListener {
    private String nodeName;
    private ScActionListener listner;

    /**
     * Construct listener.
     * @param listner object that implement ScActionListner interface
     * @param nodeName node name which will be deleted.
     */
    public ListenerForDeleteNode(ScActionListener listner, String nodeName) {
        this.nodeName = nodeName;
        this.listner = listner;
    }

    /**
     * Method that get type of event for this listener.
     * @return type of event
     */
    @Override
    public String getEventType() {
        return ScEventTypes.DELETE_SC_NODE;
    }

    /**
     * Method that will be executed in answer for the event.
     * @param event event object, contain node that will be deleted.
     */
    @Override
    public void perform(ScEvent event) {
        listner.perform(event);
    }

    /**
     * Method that determine, if this listener suitable for happened event.
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
        if (!nodeName.equals(node.getName())) {
            return false;
        }
        return true;
    }

}
