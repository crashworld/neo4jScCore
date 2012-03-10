package net.ostis.sccore.scelements;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.NotFoundException;
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

    /**
     * Method that get end sc node of sc arc.
     * If end element of sc arc is another sc arc, then return null.
     * @return founded sc node
     */
    @Override
    public ScNode getEndScNode() {
        Node endNode = endLink.getEndNode();
        try {
            String type = (String) endNode.getProperty(ScNode.SC_NODE_TYPE_PROPERTY);
            ScNodeImpl scNode = new ScNodeImpl(endNode);
            return scNode;
        } catch (NotFoundException ex) {
            /* if property not found it's not sc node*/
            return null;
        }
    }


    public Node getConnectedNode() {
        return this.connectedNode;
    }
}
