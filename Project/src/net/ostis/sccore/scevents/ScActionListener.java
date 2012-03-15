
package net.ostis.sccore.scevents;

/**
 * Interface for action that can subscribe on ScEvent.
 * @author yaskoam
 */
public interface ScActionListener {
    /**
     * Method that execute after event.
     * @param event event object
     */
    void perform(ScEvent event);
}
