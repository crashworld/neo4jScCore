package net.ostis.sccore.scelements;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import net.ostis.sccore.contents.Content;

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
        List<ScArc> allInputScArcs = new ArrayList<ScArc>();

        /* get all incoming relationship from neo4j node */
        Iterable<Relationship> allInputRelationship = neo4jNode.getRelationships(Direction.INCOMING);

        for (Relationship firstIncomingRelationship : allInputRelationship) {
            /* get connector node of sc arc */
            Node connectorNode = firstIncomingRelationship.getStartNode();

            /* get start relationships of sc arc. Can be MORE than one,
             * because sc arc can go to another sc arc.
             * Choose only thoose, whish has property SC_NODE_NAME_PROPERTY*/
            Iterable<Relationship> allStartRelationships =
                    connectorNode.getRelationships(Direction.INCOMING);

            for (Relationship secondIncomingRelationship : allStartRelationships) {
                if (secondIncomingRelationship.getStartNode().hasProperty(ScNode.SC_NODE_NAME_PROPERTY)) {

                    ScArcImpl currentScArc = new ScArcImpl(
                            secondIncomingRelationship, connectorNode, firstIncomingRelationship);

                    allInputScArcs.add(currentScArc);
                }
            }
        }

        return allInputScArcs;
    }

    /**
     * Method that get all output sc arcs from sc node.
     * @return list of sc arcs
     */
    @Override
    public List<ScArc> getAllOutputScArcs() {
        List<ScArc> allOutputScArcs = new ArrayList<ScArc>();

        /* get all outgoing relationship from neo4j node */
        Iterable<Relationship> allOutputRelationship = neo4jNode.getRelationships(Direction.OUTGOING);

        for (Relationship currentRelationship : allOutputRelationship) {
            /* get connected node of sc arc */
            Node connectedNode = currentRelationship.getEndNode();

            /* get end relationship of sc arc. It can be only one,
             * becouse sc arc can't go out from another sc arc */
            Relationship endRelationship = connectedNode.getRelationships(Direction.OUTGOING).iterator().next();
            ScArcImpl currentScArc = new ScArcImpl(currentRelationship, connectedNode, endRelationship);
            allOutputScArcs.add(currentScArc);
        }

        return allOutputScArcs;
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
