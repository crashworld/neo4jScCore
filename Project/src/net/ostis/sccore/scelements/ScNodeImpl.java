package net.ostis.sccore.scelements;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import net.ostis.sccore.contents.Content;
import net.ostis.sccore.scfactory.RelTypes;

/**
 * Class that implement SC node.
 * @author yaskoam
 */
public class ScNodeImpl extends ScNode {

    /** String constant for connector node of sc arc. */
    public static final String CONNECTORNODE = "connectorNode";
    private Node neo4jNode;
    private Content nodeContent;

    /**
     * Construct object.
     * @param node neo4jNode
     */
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
     * Method that get all input sc arcs to sc node.
     * @return list of sc arcs
     */
    @Override
    public List<ScArc> getAllInputScArcs() {
        List<ScArc> scArcsList = new ArrayList<ScArc>();
        Iterable<Relationship> endRelations = neo4jNode.getRelationships(RelTypes.endLink, Direction.INCOMING);

        for (Relationship endRelationship : endRelations) {
            Node node = endRelationship.getStartNode();
            Relationship beginRelationship = node.getSingleRelationship(RelTypes.beginLink, Direction.INCOMING);
            ScArcImpl newScArc = new ScArcImpl(beginRelationship, node, endRelationship);
            scArcsList.add(newScArc);
        }

        return scArcsList;
    }

    /**
     * Method that get all output sc arcs from sc node.
     * @return list of sc arcs
     */
    @Override
    public List<ScArc> getAllOutputScArcs() {
        List<ScArc> scArcsList = new ArrayList<ScArc>();
        Iterable<Relationship> beginRelations = neo4jNode.getRelationships(RelTypes.beginLink, Direction.OUTGOING);

        for (Relationship startRelationship : beginRelations) {
            Node node = startRelationship.getEndNode();
            Relationship endRelationship = node.getSingleRelationship(RelTypes.endLink, Direction.OUTGOING);
            ScArcImpl newScArc = new ScArcImpl(startRelationship, node, endRelationship);
            scArcsList.add(newScArc);
        }

        return scArcsList;
    }

    /**
     * Method that get all sc arcs connected with node.
     * @return list of sc arcs
     */
    @Override
    public List<ScArc> getAllScArc() {
        List<ScArc> scArcsList = this.getAllInputScArcs();
        scArcsList.addAll(this.getAllOutputScArcs());
        return scArcsList;
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
     * Method that sets type of sc element.
     * @param type type of element
     */
    @Override
    public void setType(String type) {
        //neo4jNode.setProperty(SC_NODE_TYPE_PROPERTY, type);
    }

    /**
     * Method that get type of sc node.
     * @return type of sc node
     */
    @Override
    public String getType() {
        return "";
        //return (String) neo4jNode.getProperty(SC_NODE_TYPE_PROPERTY);
    }

    public Node getNeo4jNode() {
        return this.neo4jNode;
    }
}
