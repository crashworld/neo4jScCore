package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScElement;

/**
 * Factory class that provide creation ScEventListener objects.
 *
 * @author yaskoam
 */
public class ScEventFactory {

    /**
     * Method that create suitable ScEventListener object
     * for all event excluding creation of node.
     *
     * @param eventType type of event that we want to listen
     * @param listener object that realize ScActionListener interface
     * @param scElement sc element that associated with happened event
     * @return created ScEventListener object
     */
    public static ScEventListener createScListener(ScEventTypes eventType,
        ScActionListener listener, ScElement scElement) {

        if (eventType == ScEventTypes.ATTACH_INPUT_TO_NODE) {
            return new ListenerForAttachInputToNode(listener, scElement);
        }

        if (eventType == ScEventTypes.ATTACH_OUTPUT_TO_NODE) {
            return new ListenerForAttachOutputToNode(listener, scElement);
        }

        if (eventType == ScEventTypes.DELETE_SC_NODE) {
            return new ListenerForDeleteNode(listener, scElement);
        }

        if (eventType == ScEventTypes.DETACH_OUTPUT_FROM_NODE) {
            return new ListenerForDetachOutputFromNode(listener, scElement);
        }

        if (eventType == ScEventTypes.DETACH_INPUT_FROM_ARC) {
            return new ListenerForDetachInputFromArc(listener, scElement);
        }

        if (eventType == ScEventTypes.ATTACH_INPUT_TO_ARC) {
            return new ListenerForAttachInputToArc(listener, scElement);
        }
        return null;
    }

    /**
     * Method that create ScEventListener object
     * for event of creation node.
     *
     * @param listener object that realize ScActionListener interface
     * @param nodeName name of node that associated with happened event
     * @return created ScEventListener object
     */
    public static ScEventListener createScListenerForNodeCreation(
        ScActionListener listener, String nodeName) {

        return new ListenerForCreationNode(listener, nodeName);
    }
}
