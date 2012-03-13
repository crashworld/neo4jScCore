/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;

/**
 *
 * @author yaskoam
 */
public class ListnerForRemoveNode extends ScEventListener{

    @Override
    public String getEventType() {
        return ScEventTypes.DELETE_SC_NODE;
    }

    @Override
    public void perform(ScEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean verification(ScEvent event) {
        ScElement element = event.getSource();
        if (!element.isScNode()) {
            return false;
        }

        ScNode node = (ScNode) element;
        if (!nodeName.equals(node.getName())) {
            return false;
        }
        return true;
    }

}
