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

    @Override
    public String getType() {
        return (String) connectedNode.getProperty(SC_ARC_TYPE_PROPERTY);
    }

    public Node getConnectedNode() {
        return this.connectedNode;
    }
}
