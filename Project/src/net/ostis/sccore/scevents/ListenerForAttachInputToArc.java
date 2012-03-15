/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;

/**
 * Class for listener, that wait attach input arc to another arc.
 * @author yaskoam
 */
public class ListenerForAttachInputToArc extends ScEventListener {
    private ScActionListener listner;
    private String startNodeName;

    /**
     * Construct listener.
     * @param listner object that implement ScActionListner interface
     * @param startNodeName name of node, from which an arc goes, which held the other arc.
     */
    public ListenerForAttachInputToArc(ScActionListener listner, String startNodeName) {
        this.listner = listner;
        this.startNodeName = startNodeName;
    }

    /**
     * Method that get type of event for this listener.
     * @return type of event
     */
    @Override
    public String getEventType() {
        return ScEventTypes.ATTACH_INPUT_TO_ARC;
    }

    /**
     * Method that will be executed in answer for the event.
     * @param event event object, contain new arc which was attached to other arc.
     */
    @Override
    public void perform(ScEvent event) {
        listner.perform(event);
    }

    /**
     * Method that determine, if this listener suitable for happened event.
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
        ScNode startNode = endArc.getStartScNode();

        if (!startNode.getName().equals(startNodeName)) {
            return false;
        }

        return true;
    }
}
