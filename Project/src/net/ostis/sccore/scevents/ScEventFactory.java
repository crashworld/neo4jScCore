/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ostis.sccore.scevents;

/**
 * Class that provide creation ScEventListner objects.
 * @author yaskoam
 */
public class ScEventFactory {

    /**
     * Method that create suitable ScEventListner object.
     * @param eventType type of event that we want to listen
     * @param listner object that realize ScActionListner interface
     * @param nodeName name of node that associated with happened event
     * @return created ScEventListner object
     */
    public static ScEventListener createScListner(String eventType,
        ScActionListener listner, String nodeName) {

        if (eventType.equals(ScEventTypes.CREATE_SC_NODE)) {
            return new ListenerForCreationNode(listner, nodeName);
        }

        if (eventType.equals(ScEventTypes.ATTACH_INPUT_TO_NODE)) {
            return new ListenerForAttachInputToNode(listner, nodeName);
        }

        if (eventType.equals(ScEventTypes.ATTACH_OUTPUT_TO_NODE)) {
            return new ListenerForAttachOutputToNode(listner, nodeName);
        }

        if (eventType.equals(ScEventTypes.DELETE_SC_NODE)) {
            return new ListenerForDeleteNode(listner, nodeName);
        }

        if (eventType.equals(ScEventTypes.DETACH_OUTPUT_FROM_NODE)) {
            return new ListenerForDetachOutputFromNode(listner, nodeName);
        }

        if (eventType.equals(ScEventTypes.DETACH_INPUT_FROM_ARC)) {
            return new ListenerForDetachInputFromArc(listner, nodeName);
        }

        if (eventType.equals(ScEventTypes.ATTACH_INPUT_TO_ARC)) {
            return new ListenerForAttachInputToArc(listner, nodeName);
        }
        return null;
    }
}
