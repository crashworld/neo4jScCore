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
public class ListenerForCreationNode extends ScEventListener{
    private ScActionListener listner;
    private String nodeName;

    public ListenerForCreationNode(ScActionListener listner, String nodeName) {
        this.listner = listner;
        this.nodeName = nodeName;
    }

    @Override
    public void perform(ScEvent event) {
        listner.perform(event);
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

    @Override
    public String getEventType() {
        return ScEventTypes.CREATE_SC_NODE;
    }


}
