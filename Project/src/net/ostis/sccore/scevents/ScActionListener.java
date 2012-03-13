/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
    public void perform(ScEvent event);
}
