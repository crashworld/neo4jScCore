package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScElement;

/**
 * Factory class that provide creation ScEventListner objects.
 *
 * @author yaskoam
 */
public class ScEventFactory {

    /**
     * Method that create suitable ScEventListner object
     * for all event excluding creation of node.
     *
     * @param eventType type of event that we want to listen
     * @param listner object that realize ScActionListner interface
     * @param scElement sc element that associated with happened event
     * @return created ScEventListner object
     */
    public static ScEventListener createScListner(ScEventTypes eventType,
        ScActionListener listner, ScElement scElement) {

        if (eventType == ScEventTypes.ATTACH_INPUT_TO_NODE) {
            return new ListenerForAttachInputToNode(listner, scElement);
        }

        if (eventType == ScEventTypes.ATTACH_OUTPUT_TO_NODE) {
            return new ListenerForAttachOutputToNode(listner, scElement);
        }

        if (eventType == ScEventTypes.DELETE_SC_NODE) {
            return new ListenerForDeleteNode(listner, scElement);
        }

        if (eventType == ScEventTypes.DETACH_OUTPUT_FROM_NODE) {
            return new ListenerForDetachOutputFromNode(listner, scElement);
        }

        if (eventType == ScEventTypes.DETACH_INPUT_FROM_ARC) {
            return new ListenerForDetachInputFromArc(listner, scElement);
        }

        if (eventType == ScEventTypes.ATTACH_INPUT_TO_ARC) {
            return new ListenerForAttachInputToArc(listner, scElement);
        }
        return null;
    }

    /**
     * Method that create ScEventListner object
     * for event of creation node.
     *
     * @param listner object that realize ScActionListner interface
     * @param nodeName name of node that associated with happened event
     * @return created ScEventListner object
     */
    public static ScEventListener createScListnerForNodeCreation(
        ScActionListener listner, String nodeName) {

        return new ListenerForCreationNode(listner, nodeName);
    }
}
