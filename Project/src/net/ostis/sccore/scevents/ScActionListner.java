/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ostis.sccore.scevents;

/**
 * Interface for action that can subscribe on ScEvent.
 * @author yaskoam
 */
public interface ScActionListner {
    /**
     * Method that execute after event.
     * @param event event object
     */
    public void perform(ScEvent event);

    /**
     * Method that check event is suitable or not.
     * @param event event object
     * @return return true if action must be performed, in other case return false
     */
    public boolean verification(ScEvent event);
}
