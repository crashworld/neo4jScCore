package net.ostis.sccore.scelements;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

/**
 * User: yaskoam
 * Date: 03.03.12
 * Time: 13:40
 */
public class ScArcImpl extends ScArc {
    private Relationship beginLink;
    private Node connectedNode;
    private Relationship endLink;

    public ScArcImpl(Relationship beginLink, Node connectedNode, Relationship endLink) {
        this.beginLink = beginLink;
        this.connectedNode = connectedNode;
        this.endLink = endLink;
    }

    /**
     * Method that returns true if element is arc and false if not.
     * @return true if is arc
     */
    @Override
    public boolean isScArc() {
        return true;
    }

    /**
     * Method that returns true if element is node and false if not.
     * @return true if is node
     */
    @Override
    public boolean isScNode() {
        return false;
    }

    /**
     * Method that sets type of sc arc
     * @param type type of element
     */
    @Override
    public void setType(String type) {
        connectedNode.setProperty(SC_ARC_TYPE_PROPERTY, type);
    }

    /**
     * Method that get type of sc arc
     * @return type of element
     */
    @Override
    public String getType() {
        return (String) connectedNode.getProperty(SC_ARC_TYPE_PROPERTY);
    }

    public Node getConnectedNode() {
        return this.connectedNode;
    }
}
