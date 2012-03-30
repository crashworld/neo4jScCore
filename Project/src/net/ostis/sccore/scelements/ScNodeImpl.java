package net.ostis.sccore.scelements;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import net.ostis.sccore.contents.Content;
import net.ostis.sccore.scfactory.RelTypes;
import net.ostis.sccore.types.ScElementTypes;

/**
 * Class that implement SC node.
 * 
 * @author yaskoam
 */
public class ScNodeImpl extends ScNode {

    /** String constant for connector node of sc arc. */
    public static final String CONNECTORNODE = "_connectorNode";

    private Node neo4jNode;

    private Content nodeContent;

    /**
     * Construct object.
     *
     * @param node neo4jNode
     */
    public ScNodeImpl(Node node) {
        this.neo4jNode = node;
    }

    /**
     * Method that returns true if element is arc and false if not.
     *
     * @return true if is arc
     */
    @Override
    public boolean isScArc() {
        return false;
    }

    /**
     * Method that returns true if element is node and false if not.
     *
     * @return true if is node
     */
    @Override
    public boolean isScNode() {
        return true;
    }

     /**
     * Method that sets type of sc arc.
     *
     * @param type type of element
     */
    @Override
    public void addType(ScElementTypes type) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method that set types of sc element.
     *
     * @param types list of types name
     */
    @Override
    public void addTypes(List<ScElementTypes> types) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method that get all types of sc elemetn.
     *
     * @return list of types
     */
    @Override
    public List<ScElementTypes> getTypes() {
        return null;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method that remove type from sc element.
     *
     * @param type name of type
     */
    @Override
    public void removeType(ScElementTypes type) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method that get name of sc node.
     *
     * @return name of sc node
     */
    @Override
    public String getName() {
        return (String) neo4jNode.getProperty(SC_NODE_NAME_PROPERTY);
    }

    /**
     * Method that set name for sc node.
     *
     * @param name node name
     */
    @Override
    public void setName(String name) {
        neo4jNode.setProperty(ScNode.SC_NODE_NAME_PROPERTY, name);
    }

    /**
     * Method that get all input sc arcs to sc node.
     *
     * @return list of sc arcs
     */
    @Override
    public List<ScArc> getAllInputScArcs() {
        List<ScArc> scArcsList = new ArrayList<ScArc>();
        Iterable<Relationship> endRelations = neo4jNode.getRelationships(RelTypes.endLink, Direction.INCOMING);

        for (Relationship endRelationship : endRelations) {
            Node connector = endRelationship.getStartNode();
            scArcsList.add(new ScArcImpl(connector));
        }

        return scArcsList;
    }

    /**
     * Method that get all output sc arcs from sc node.
     *
     * @return list of sc arcs
     */
    @Override
    public List<ScArc> getAllOutputScArcs() {
        List<ScArc> scArcsList = new ArrayList<ScArc>();
        Iterable<Relationship> beginRelations = neo4jNode.getRelationships(RelTypes.beginLink, Direction.OUTGOING);

        for (Relationship startRelationship : beginRelations) {
            Node connector = startRelationship.getEndNode();
            scArcsList.add(new ScArcImpl(connector));
        }

        return scArcsList;
    }

    /**
     * Method that get all sc arcs connected with node.
     * 
     * @return list of sc arcs
     */
    @Override
    public List<ScArc> getAllScArc() {
        List<ScArc> scArcsList = this.getAllInputScArcs();
        scArcsList.addAll(this.getAllOutputScArcs());
        return scArcsList;
    }

    public Node getNeo4jNode() {
        return this.neo4jNode;
    }
}
