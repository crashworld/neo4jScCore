package net.ostis.sccore.scelements;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import net.ostis.sccore.scfactory.RelTypes;

/**
 * Class that implement sc arc.
 * 
 * @author yaskoam
 */
public class ScArcImpl extends ScArc {
    
    private Relationship beginLink;
    private Node connectorNode;
    private Relationship endLink;

    /**
     * Constructor for object.
     * @param beginLink begin relationship of arc
     * @param connectorNode connector node of arc
     * @param endLink end relationship of arc
     */
    public ScArcImpl(Relationship beginLink, Node connectorNode, Relationship endLink) {
        this.beginLink = beginLink;
        this.connectorNode = connectorNode;
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
     * Method that returns true if element is connector and false if not.
     * @return true if is connector
     */
    @Override
    public boolean isScNode() {
        return false;
    }

    /**
     * Method that sets type of sc arc.
     * @param type type of element
     */
    @Override
    public void setType(String type) {
        //connector.setProperty(SC_ARC_TYPE_PROPERTY, type);
    }

    /**
     * Method that get type of sc arc.
     * @return type of element
     */
    @Override
    public String getType() {
        return "";
        //return (String) connector.getProperty(SC_ARC_TYPE_PROPERTY);
    }

    /**
     * Gets start sc connector of sc arc.
     * If start element of sc arc is another sc arc, then return null.
     * @return founded sc connector
     */
    @Override
    public ScNode getStartScNode() {
        Node startNode = beginLink.getStartNode();
        ScNodeImpl scNode = new ScNodeImpl(startNode);
        return scNode;
    }

    /**
     * Method that get end sc connector of sc arc.
     * If end element of sc arc is another sc arc, then return null.
     * @return founded sc node
     */
    @Override
    public ScNode getEndScNode() {
        Node endNode = endLink.getEndNode();
        if (endNode.hasProperty(ScNodeImpl.CONNECTORNODE)) {
            return null;
        }
        ScNodeImpl scNode = new ScNodeImpl(endNode);
        return scNode;
    }

    /**
     * Method that get end sc arc of sc arc.
     * If end element of sc arc is sc connector, then return null.
     * @return founded sc arc
     */
    @Override
    public ScArc getEndScArc() {
        Node connector = endLink.getEndNode();
        if (!connector.hasProperty(ScNodeImpl.CONNECTORNODE)) {
            return null;
        }

        Relationship begin = connector.getSingleRelationship(RelTypes.beginLink, Direction.INCOMING);
        Relationship end = connector.getSingleRelationship(RelTypes.endLink, Direction.OUTGOING);
        ScArcImpl arc = new ScArcImpl(begin, this.connectorNode, end);
        return arc;
    }

    /**
     * Method that get all input sc arcs.
     * @return all input sc arcs
     */
    @Override
    public List<ScArc> getAllInputScArcs() {
        List<ScArc> scArcsList = new ArrayList<ScArc>();
        Iterable<Relationship> endRelations = connectorNode.getRelationships(RelTypes.endLink, Direction.INCOMING);

        for (Relationship currentRelationship : endRelations) {
            Node node = currentRelationship.getStartNode();
            Relationship beginRelationship = node.getSingleRelationship(RelTypes.beginLink, Direction.INCOMING);
            ScArcImpl newScArc = new ScArcImpl(beginRelationship, node, currentRelationship);
            scArcsList.add(newScArc);
        }
        
        return scArcsList;
    }
    
    /**
     * Gets connector that used like arc.
     * @return connector-connector
     */
    public Node getArcConnectorNode() {
        return this.connectorNode;
    }
}
