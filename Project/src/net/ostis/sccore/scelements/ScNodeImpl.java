package net.ostis.sccore.scelements;

import org.neo4j.graphdb.Node;

/**
 * User: yaskoam
 * Date: 03.03.12
 * Time: 13:39
 */
public class ScNodeImpl extends ScNode {
    private Node neo4jNode;

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
