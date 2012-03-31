/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ostis.sccore.scevents;

/**
 * Class that present listener for event.
 * @author yaskoam
 */
public abstract class ScEventListener {
    /**
     * Method that check event is suitable or not.
     * @param event event object
     * @return return true if action must be performed, in other case return false
     */
    public abstract boolean verification(ScEvent event);
    /**
     * Method that execute after event.
     * @param event event object
     */
    public abstract void perform(ScEvent event);

    /**
     * Method that return type of event that listener wait.
     * @return event type constant
     */
    public abstract ScEventTypes getEventType();
}
