/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;

/**
 * Class for listener, that wait detach input arc from another arc.
 * @author yaskoam
 */
public class ListenerForDetachInputFromArc extends ScEventListener {
    private ScActionListener listener;
    private String startNodeName;

    /**
     * Construct listener.
     * @param listner object that implement ScActionListner interface
     * @param startNodeName name of node, from which an arc goes, from which another arc was detached.
     */
    public ListenerForDetachInputFromArc(ScActionListener listner, String startNodeName) {
        this.listener = listner;
        this.startNodeName = startNodeName;
    }

    /**
     * Method that get type of event for this listener.
     * @return type of event
     */
    @Override
    public String getEventType() {
        return ScEventTypes.DETACH_INPUT_FROM_ARC;
    }

    /**
     * Method that will be executed in answer for the event.
     * @param event event object, contain sc arc from which other arc was detach.
     */
    @Override
    public void perform(ScEvent event) {
        listener.perform(event);
    }

    /**
     * Method that determine, if this listener suitable for happened event.
     * @param event event object, contain sc arc from which other arc was detach.
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

        if (!startNode.getName().equals(startNodeName)) {
            return false;
        }

        return true;
    }

}
