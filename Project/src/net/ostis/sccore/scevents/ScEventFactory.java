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
            return new ListnerForAttachInputToNode(listner, nodeName);
        }

        return null;
    }
}
