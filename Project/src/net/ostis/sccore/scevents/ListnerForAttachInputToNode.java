/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.ostis.sccore.scevents;

import net.ostis.sccore.scelements.ScArc;
import net.ostis.sccore.scelements.ScElement;
import net.ostis.sccore.scelements.ScNode;

/**
 *
 * @author yaskoam
 */
public class ListnerForAttachInputToNode extends ScEventListener{
    private ScActionListener listner;
    private String nodeName;

    public ListnerForAttachInputToNode(ScActionListener listner, String nodeName) {
        this.listner = listner;
        this.nodeName = nodeName;
    }

    @Override
    public String getEventType() {
        return ScEventTypes.ATTACH_INPUT_TO_NODE;
    }

    @Override
    public void perform(ScEvent event) {
        listner.perform(event);
    }

    @Override
    public boolean verification(ScEvent event) {
        ScElement element = event.getSource();
        if (!element.isScArc()) {
            return false;
        }

        ScArc arc = (ScArc) element;
        ScNode endNode = arc.getEndScNode();
        if (endNode == null) {
            return false;
        }

        if (!endNode.getName().equals(nodeName)) {
            return false;
        }

        return true;
    }

}
