package net.ostis.sccore.scelements;

import net.ostis.sccore.contents.Content;
import org.neo4j.graphdb.Node;

/**
 * User: yaskoam
 * Date: 03.03.12
 * Time: 13:39
 */
public class ScNodeImpl extends ScNode {
    private Node neo4jNode;
    private Content nodeContent;

    public ScNodeImpl(Node node) {
        this.neo4jNode = node;
    }

    /**
     * Method that get name of sc node.
     * @return name of sc node
     */
    @Override
    public String getName() {
        return (String) neo4jNode.getProperty(SC_NODE_NAME_PROPERTY);
    }

    /**
     * Method that returns true if element is arc and false if not.
     * @return true if is arc
     */
    @Override
    public boolean isScArc() {
        return false;
    }

    /**
     * Method that returns true if element is node and false if not.
     * @return true if is node
     */
    @Override
    public boolean isScNode() {
        return true;
    }

    /**
     * Method that sets type of sc element
     * @param type type of element
     */
    @Override
    public void setType(String type) {
        neo4jNode.setProperty(SC_NODE_TYPE_PROPERTY, type);
    }


    /**
     * Method that get type of sc node.
     * @return type of sc node
     */
    @Override
    public String getType() {
        return (String) neo4jNode.getProperty(SC_NODE_TYPE_PROPERTY);
    }

    public Node getNeo4jNode() {
        return this.neo4jNode;
    }
}
